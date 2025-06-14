package com.und.server.backup.scheduler;

import com.und.server.backup.service.CardDailyBackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CardDailyBackupScheduler {

    private final CardDailyBackupService cardDailyBackupService;

    @Scheduled(cron = "0 0 0 * * *")
    public void backupDailyCards() {
        cardDailyBackupService.dailyBackup();
    }

}
