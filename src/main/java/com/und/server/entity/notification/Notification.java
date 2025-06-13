package com.und.server.entity.notification;

import com.und.server.constants.NotificationType;
import com.und.server.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_notification")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "notification_type")
public abstract class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_id")
    private Long id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;


    public abstract NotificationType getType();

}
