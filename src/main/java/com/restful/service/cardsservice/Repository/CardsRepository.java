package com.restful.service.cardsservice.Repository;

import com.restful.service.cardsservice.model.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Card, Long> {

    Optional<Card> findAllByUser(Long id, Pageable pageable);
    List<Card> findAllByColour(String colour, Pageable pageable);

    List<Card> findAllByState(String state, Pageable pageable);

    List<Card> findAllByCreated(Timestamp timestamp, Pageable pageable);

    List<Card> findAllByName(String name, Pageable pageable);
}
