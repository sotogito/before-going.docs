package com.und.server.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_user")
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Card> cardList;

}
