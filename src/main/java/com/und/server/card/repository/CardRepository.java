package com.und.server.card.repository;

import com.und.server.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("""
             select distinct c
             from Card c
                 join fetch c.user
                 join fetch c.notification
                 join fetch c.checkList
             where c.backupDate is null
            """)
    List<Card> findAllOfToday();

}