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
            cardRepository.save(cardForBackup); //영속성 전이 설정으로 checklist는 save 안해도 됨
        }
    }

    @Override
    public void defineTodayCard() {
        /**
         * 오늘 날짜로 된 데이터가 있는 카드를가져와서
         * 만약 있으면 해당 카드의
         * 1. member
         * 2. card
         * 와 같은
         * 3. backupDate가 null
         * 인 카드를 가져와서
         * 미래 추가된 체크리스트를 추가
         */
        LocalDate today = LocalDate.now();
        List<Card> cardForAddCheckList = cardRepository.findAllByBackupDate(today);

        for (Card card : cardForAddCheckList) {
            List<CheckList> checkListToAdd = card.getCheckList().stream() ///복사하는 로ㅓ직은 중복되는 경우가 많기 때문에 엔티티 내부에 작성
                    .map(checkList -> CheckList.builder()
                            .content(checkList.getContent())
                            .checkListType(checkList.getCheckListType())
                            .isActive(checkList.isActive())
                            .build()
                    ).toList();
            Card todayCard = cardRepository.findByMemberIdAndCardIdAndBackupDate(
                    card.getId(),
                    card.getMember().getId(),
                    LocalDate.now()
            );

            checkListToAdd.forEach(newCheckList -> todayCard.addCheckList(newCheckList));
            cardRepository.delete(card);
        }
    }

}
