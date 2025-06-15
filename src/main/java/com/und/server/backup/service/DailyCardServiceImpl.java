package com.und.server.backup.service;

import com.und.server.backup.common.CardBackupFactory;
import com.und.server.card.entity.Card;
import com.und.server.card.entity.CheckList;
import com.und.server.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@EnableScheduling
@Service
public class DailyCardServiceImpl implements DailyCardService {

    private final CardRepository cardRepository;
    private final CardBackupFactory cardBackupFactory;

    @Override
    public void dailyBackup() {
        List<Card> cardList = cardRepository.findAllOfToday();

        for (Card card : cardList) {
            Card cardForBackup = cardBackupFactory.backupToYesterday(card);
            cardRepository.save(cardForBackup);
        }
    }

    @Override
//    @Transactional -> 회원별로 트랜젝션 처리 고려 Spring Batch??? +_ 페이징처리
    /**
     * 이렇게 모든 사용자의 모든 카드를 한버에 가져와서 처리하는 게 맞는가?:
     * 그리고 이 설계에 트렌젝션을 주는게 맞나?
     * 일단 회원을 가져오고 회원의 오늘 카드를 가져오고 거기에 트렌젝션을?
     * 그넫 만약 사용자 100만명중 미래카드가 있는 회원이 1명이라면 100만명 다 가져와야하나>?
     * 미래 카드를 가져와서 적용하는게 맞느거같기도 하고
     *
     * 아니면 오늘 처리할 카드가 있는 회원id 리스트를 받아오나
     */
    public void defineTodayCard() {
        LocalDate today = LocalDate.now();
        List<Card> cardForAddCheckList = cardRepository.findAllByBackupDate(today);

        for (Card card : cardForAddCheckList) {
            List<CheckList> checkListForAdd = card.getCheckList().stream()
                    .map(CheckList::deepClone)
                    .toList();

            Card todayCard = cardRepository.findByMemberIdAndCardNameAndBackupDateIsNull(
                    card.getMember().getId(),
                    card.getCardName()
            ).orElseThrow(IllegalArgumentException::new);

            checkListForAdd.forEach(todayCard::addCheckList);

            cardRepository.save(todayCard);
            cardRepository.delete(card);
        }
    }

}
