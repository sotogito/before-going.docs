package com.und.server.card.entity;

import com.und.server.card.CheckListType;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

@Entity
@Table(name = "tbl_checklist")
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id", nullable = false)
    private Long id;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "checklist_type", nullable = false)
    private CheckListType checkListType;

    @Setter
    @Column(name = "is_checked", nullable = false)
    private boolean isChecked = false;


    public CheckList deepClone() {
        return CheckList.builder()
                .content(this.content)
                .checkListType(this.checkListType)
                .isChecked(this.isChecked)
                .build();
    }

}
