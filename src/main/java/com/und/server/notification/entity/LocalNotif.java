package com.und.server.notification.entity;

import com.und.server.notification.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "radius", nullable = false, precision = 10, scale = 2)
    private BigDecimal radius;

    @Override
    public NotificationType getType() {
        return NotificationType.LOCATION;
    }

}
