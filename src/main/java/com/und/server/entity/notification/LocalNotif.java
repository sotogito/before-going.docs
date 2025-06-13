package com.und.server.entity.notification;

import com.und.server.constants.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_notif_location")
@DiscriminatorValue("LOCATION")
public class LocalNotif extends Notification {

    private final static double DEFAULT_TRIGGER_RADIUS_KM = 5.0;

    private String location;
    //장소 정보, 알람 시간 정보, 반경 범위 정도

//    public double calculateTriggerRadius() {
//        // 거리 계산
//        // 만약 이동 거리가 기본값인 5km보다 짧을 경우
//        // 80%거리를 계산하여 알람이 울릴 거리 지정
//    }

    @Override
    public NotificationType getType() {
        return NotificationType.LOCATION;
    }

}
