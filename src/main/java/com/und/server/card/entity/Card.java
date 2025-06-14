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
@Table(name = "tbl_card")
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
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkList = new ArrayList<>();

    @Column(name = "used_date", nullable = true)
    private LocalDate backupDate;

//    @Column(name = "is_use", nullable = false)
//    private Boolean isUse; //기간이 지난 카드를 삭제하지 않을거면 둬야할듯


    public void addCheckList(CheckList checkList) {
        this.checkList.add(checkList);
        checkList.setCard(this);
    }

}
