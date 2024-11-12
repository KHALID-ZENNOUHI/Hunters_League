package org.springboot.hunters_league.service;

import lombok.Setter;
import org.springboot.hunters_league.repository.dto.CompetitionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.repository.CompetitionRepository;
import org.springboot.hunters_league.web.error.CompetitionNotFoundException;
import org.springboot.hunters_league.web.error.ExistCompetitionInTheSameWeekException;
import org.springboot.hunters_league.web.error.InvalidParticipantsRangeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;
    @Value("${competition.scheduleStartTime}")
    private String scheduleStartTime;

    public CompetitionService(CompetitionRepository competitionRepository, TaskScheduler taskScheduler) {
        this.competitionRepository = competitionRepository;
        this.taskScheduler = taskScheduler;
    }

    public Competition save(Competition competition) {
        competition.setCode(competition.getLocation().toLowerCase(Locale.ROOT) + "_" + competition.getDate().getDayOfMonth() + "_" + competition.getDate().getMonthValue() + "_" + competition.getDate().getYear());
        validateMinParticipantsLessThanMax(competition.getMinParticipants(), competition.getMaxParticipants());
        checkIfThereIsOneCompetitionInTheWeek(competition);
        return competitionRepository.save(competition);
    }

    public Competition update(Competition competition) {
        findById(competition.getId());
        return competitionRepository.save(competition);
    }

    public void delete(UUID id) {
        competitionRepository.deleteById(id);
    }

    public Competition findById(UUID id) {
        return competitionRepository.findById(id).orElseThrow(CompetitionNotFoundException::new);
    }

    public CompetitionDTO competitionDetails(UUID id) {
        return competitionRepository.competitionDetails(id);
    }

    public Page<Competition> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return competitionRepository.findAll(pageable);
    }

    public void checkIfThereIsOneCompetitionInTheWeek(Competition competition) {
        Optional<Competition> competitionOptional = competitionRepository.findCompetitionInSameWeek(competition.getDate());
        if (competitionOptional.isPresent()) {
            throw new ExistCompetitionInTheSameWeekException();
        }
    }

    public void validateMinParticipantsLessThanMax(Integer min, Integer max) {
        if (min > max) {
            throw new InvalidParticipantsRangeException();
        }
    }

    @Scheduled(cron = "${competition.scheduleStartTime}") // Runs every Monday at midnight
    public void scheduleWeeklyCompetitionCheck() {
        LocalDateTime now = LocalDateTime.now();

        // Calculate the start and end of the current week
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(0).withMinute(0);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        // Find the competition for the current week
        Optional<Competition> competitionOptional = competitionRepository.findCompetitionForCurrentWeek(startOfWeek, endOfWeek);

        if (competitionOptional.isPresent()) {
            Competition competition = competitionOptional.get();
            scheduleRegistrationClosure(competition);
        }
    }

    private void scheduleRegistrationClosure(Competition competition) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime closeTime = competition.getDate().minusHours(24);

        // If the closure time is in the past, close the registrations immediately
        if (closeTime.isBefore(now)) {
            closeRegistrationsForCompetition(competition);
        } else {
            // Convert LocalDateTime to Instant
            Instant closeTimeInstant = closeTime.atZone(ZoneId.systemDefault()).toInstant();

            // Schedule the task to close registrations 24 hours before the competition starts
            long delay = Duration.between(now, closeTime).toMillis();
            scheduledFuture = taskScheduler.schedule(
                    () -> closeRegistrationsForCompetition(competition),
                    closeTimeInstant
            );
        }
    }

    @Transactional
    public void closeRegistrationsForCompetition(Competition competition) {
        if (competition.getOpenRegistration()) {
            competition.setOpenRegistration(false);
            competitionRepository.save(competition);
        }
    }

}
