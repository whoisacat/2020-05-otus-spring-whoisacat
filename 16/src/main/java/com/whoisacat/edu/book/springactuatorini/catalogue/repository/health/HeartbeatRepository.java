package com.whoisacat.edu.book.springactuatorini.catalogue.repository.health;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.health.Heartbeat;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartbeatRepository extends CrudRepository<Heartbeat, Long> {

    List<Heartbeat> getAllByHeartbeatTimeIsAfter(LocalDateTime time);

    void deleteAllByHeartbeatTimeIsBefore(LocalDateTime time);
}
