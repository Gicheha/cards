package com.restful.service.cardsservice.Repository;

import com.restful.service.cardsservice.model.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByColour(String colour, Pageable pageable);

    List<Card> findAllByState(String state, Pageable pageable);

    List<Card> findAllByName(String name, Pageable pageable);

    List<Card> findByCreatedAfter(Date date, Pageable pageable);

}
