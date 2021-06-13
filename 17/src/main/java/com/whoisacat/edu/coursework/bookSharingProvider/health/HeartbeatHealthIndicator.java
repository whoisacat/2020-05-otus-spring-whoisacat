package com.whoisacat.edu.coursework.bookSharingProvider.health;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.health.Heartbeat;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.health.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class HeartbeatHealthIndicator implements HealthIndicator {

    @Value("${com.whoisacat.bookSharingProvider.health.heartbeat.period}")
    private int HEARTBEAT_PERIOD = 1000;
    private final HeartbeatRepository repository;

    public HeartbeatHealthIndicator(
            HeartbeatRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        LocalDateTime now = LocalDateTime.now();
        List<Heartbeat> heartbeats;
        try {
            heartbeats = repository.getAllByHeartbeatTimeIsAfter(now.minusSeconds(HEARTBEAT_PERIOD / 1000 * 5));
        } catch(Exception e) {
            return Health.down().withDetail("cause", repository.getClass().getSimpleName() + " unavailable").build();
        }
        if (heartbeats.size() < HEARTBEAT_PERIOD / 1000 * 4) {
            if (heartbeats.stream().anyMatch(h -> h.getHeartbeatTime().isAfter(now.minusSeconds(HEARTBEAT_PERIOD / 1000 * 2)))) {
                return Health.status("RISE").withDetail("count_of_heartbeats", heartbeats.size()).build();
            } else {
                return Health.down().build();
            }
        } else {
            return Health.up().build();
        }
    }

    @Scheduled(fixedDelayString = "${com.whoisacat.bookSharingProvider.health.heartbeat.period}", initialDelay=1000)
    public void heartbeat() {
        repository.save(new Heartbeat(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void clearHeartbeat() {
        repository.deleteAllByHeartbeatTimeIsBefore(LocalDateTime.now().minusMonths(1));
    }
}
