package com.und.server.notification.entity;

import com.und.server.notification.NotificationType;
import com.und.server.common.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_notification")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "notif_type")
public abstract class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_id")
    private Long id;

    @Column(name = "is_active", nullable = false) //todo 알림은 상태의 확장 가능성이 있나?
    private boolean isActive = true;


    public abstract NotificationType getType();

}
