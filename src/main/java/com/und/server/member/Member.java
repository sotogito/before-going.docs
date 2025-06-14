package com.und.server.member;

import com.und.server.common.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_member")
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;

}
