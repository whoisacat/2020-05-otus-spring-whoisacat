package com.whoisacat.edu.book.springactuatorini.catalogue.health;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.health.Heartbeat;
import com.whoisacat.edu.book.springactuatorini.catalogue.repository.health.HeartbeatRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class HeartbeatHealthIndicator implements HealthIndicator {

    private final HeartbeatRepository repository;

    public HeartbeatHealthIndicator(
            HeartbeatRepository repository) {
        this.repository = repository;
    }

    @Override public Health health() {
        LocalDateTime now = LocalDateTime.now();
        List<Heartbeat> heartbeats = repository.getAllByHeartbeatTimeIsAfter(now.minusSeconds(5));
        if (heartbeats.size() < 4) {
            if (heartbeats.stream().anyMatch(h -> h.getHeartbeatTime().isAfter(now.minusSeconds(2)))) {
                return Health.status("RISE").withDetail("Count of heartbets", heartbeats.size()).build();
            } else {
                return Health.down().build();
            }
        } else {
            return Health.up().build();
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void heartbeat() {
        repository.save(new Heartbeat(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void clearHeartbeat() {
        repository.deleteAllByHeartbeatTimeIsBefore(LocalDateTime.now().minusMonths(1));
    }
}
