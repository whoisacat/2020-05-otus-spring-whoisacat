package com.whoisacat.edu.coursework.bookSharingProvider.domain.health;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "heartbeat")
public class Heartbeat {

    @Id
    @SequenceGenerator(name="heartbeat", sequenceName = "heartbeat_seq",allocationSize = 1)
    @GeneratedValue(generator="heartbeat_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "heartbeat", nullable = false)
    private LocalDateTime heartbeatTime;

    public Heartbeat() {
    }

    public Heartbeat(LocalDateTime heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(LocalDateTime title) {
        this.heartbeatTime = title;
    }
}
