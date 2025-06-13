package com.und.server.entity;

import com.und.server.constants.CheckListType;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_checklist")
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Enumerated(EnumType.STRING)
    private CheckListType checkListType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive; //fixme 상수로?

}
