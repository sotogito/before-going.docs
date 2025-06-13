package com.und.server.entity.notification;

import com.und.server.constants.NotificationType;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tbl_notif_time")
@DiscriminatorValue("TIME")
public class TimeNotif extends Notification {

    private LocalTime time;

    @Override
    public NotificationType getType() {
        return NotificationType.TIME;
    }

}
