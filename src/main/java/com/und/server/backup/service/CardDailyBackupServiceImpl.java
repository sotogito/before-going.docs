package com.und.server.backup.service;

import com.und.server.backup.common.CardBackupFactory;
import com.und.server.card.entity.Card;
import com.und.server.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@EnableScheduling
@Service
public class CardDailyBackupServiceImpl implements CardDailyBackupService {

    private final CardRepository cardRepository;
    private final CardBackupFactory cardBackupFactory;

    @Override
    public void dailyBackup() {
        List<Card> cardList = cardRepository.findAllOfToday();

        for (Card card : cardList) {
            Card cardForBackup = cardBackupFactory.backupToYesterday(card);
            cardRepository.save(cardForBackup); //영속성 전이 설정으로 checklist는 save 안해도 됨
        }
    }

}
