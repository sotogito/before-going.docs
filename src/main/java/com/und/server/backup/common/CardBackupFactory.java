package com.und.server.backup.common;

import com.und.server.card.entity.Card;
import com.und.server.card.entity.CheckList;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CardBackupFactory {

    /**
     * 데일리 백업 : [카드+어제날짜]
     * 미래 백업 : [카드+미래날짜] => 사용자가 추가한 checkList만 add
     */

    public Card backupToYesterday(Card card) {
        return clone(card, LocalDate.now().minusDays(1));
    }

    public Card clone(Card original, LocalDate date) { ///FETCH로 받아와야할듯
        Card cloneCard = Card.builder()
                .member(original.getMember())
                .notification(original.getNotification())
                .backupDate(date) //백업시에는 null -> 백업 날짜
                .build();

        List<CheckList> cloneCheckLists = original.getCheckList().stream()
                .map(checkList -> CheckList.builder()
//                        .card(cloneCard) -> cloneCard.addCheckList()에서 처리하고있음
                                .content(checkList.getContent())
                                .checkListType(checkList.getCheckListType())
                                .isActive(checkList.isActive())
                                .build()
                ).toList();

        cloneCheckLists.stream().forEach(checkList -> cloneCard.addCheckList(checkList));

        return cloneCard;
    }


}
