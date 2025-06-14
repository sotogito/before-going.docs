package com.und.server.backup.scheduler;

import com.und.server.backup.service.DailyCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CardDailyBackupScheduler {

    private final DailyCardService dailyCardService;

    /**
     * 00시가 되면
     * 1. 현제 default -> 어제 날자로 백업
     * 2. 오늘 날짜로 추가되어있는 카드 있나 확인하고 있으면 checkList 추가
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void backupDailyCards() {
        dailyCardService.dailyBackup();

        /**
         * 만약 오늘 날짜로 저장된 [card+오늘만추가checkList]가 있다면
         * default(backup==null)인 card에 checkList만 추가
         *
         * 없으면 그냥 냅둠
         */
    }

}
