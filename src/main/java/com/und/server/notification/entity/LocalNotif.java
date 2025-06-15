package com.und.server.notification.entity;

import com.und.server.notification.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_notif_location")
@DiscriminatorValue("LOCATION")
public class LocalNotif extends Notification {

    private final static double DEFAULT_TRIGGER_RADIUS_KM = 5.0;

    @Column(name = "location_name", nullable = false, length = 20)
    private String locationName;

    @Column(name = "location", nullable = false, length = 200)
    private String location;


    @Override
    public NotificationType getType() {
        return NotificationType.LOCATION;
    }

}
