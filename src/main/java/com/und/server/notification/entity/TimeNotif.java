package com.und.server.notification.entity;

import com.und.server.notification.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_notif_time")
@DiscriminatorValue("TIME")
public class TimeNotif extends Notification {

    @Column(name = "time", nullable = false)
    private LocalTime time;


    @Override
    public NotificationType getType() {
        return NotificationType.TIME;
    }

}
