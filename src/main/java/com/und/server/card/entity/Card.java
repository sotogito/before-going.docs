package com.und.server.card.entity;

import com.und.server.common.BaseTimeEntity;
import com.und.server.member.Member;
import com.und.server.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

@Entity
@Table(name = "tbl_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_cardName_backupDate",
                        columnNames = {"member_id", "card_name", "backup_date"}
                )
        }
)
public class Card extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "card_name", nullable = false, length = 10)
    private String cardName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkList = new ArrayList<>();

    @Column(name = "backup_date")
    private LocalDate backupDate;


    public void addCheckList(CheckList checkList) {
        this.checkList.add(checkList);
        checkList.setCard(this);
    }

    public Card deppClone(LocalDate backupDate) {
        Card cloneCard = Card.builder()
                .member(this.member)
                .cardName(this.cardName)
                .backupDate(backupDate)
                .build();
        this.checkList.stream()
                .map(CheckList::deepClone)
                .forEach(cloneCard::addCheckList);

        return cloneCard;
    }

}
