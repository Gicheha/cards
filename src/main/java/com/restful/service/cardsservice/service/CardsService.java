package com.restful.service.cardsservice.service;

import com.restful.service.cardsservice.Repository.CardsRepository;
import com.restful.service.cardsservice.model.Card;
import com.restful.service.cardsservice.model.CardsDto;
import com.restful.service.cardsservice.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardsService {

    private CardsRepository cardsRepository;

    @Autowired
    public void setCardsRepository(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    @Transactional
    public List<Card> getAllCards(Pageable pageable){
        List<Card> cardsList = new ArrayList<>();
        cardsRepository.findAll(pageable).forEach(cardsList::add);

        if(getUser().getRole().equals("admin")){
            return cardsList;
        }{
            return cardsList.stream().filter(card -> card.getUser() == getUser()).collect(Collectors.toList());
        }

    }

    @Transactional
    public Optional<Card> getCardById(Long id){
        var card = cardsRepository.findById(id);

        if(getUser().getRole().equals("admin")){
            return card;
        }

        if(card.isPresent() && card.get().getUser() == getUser()){

            return card;
        }

        return Optional.empty();
    }

    @Transactional
    public List<Card> getCardsByColor(String colour, Pageable pageable){

        var cards = cardsRepository.findAllByColour(colour,pageable);

        if(getUser().getRole().equals("admin")){
            return cards;
        }else{
            return cards.stream().filter(card -> card.getUser() == getUser()).collect(Collectors.toList());
        }

    }

    @Transactional
    public List<Card> getCardsByStatus(String status, Pageable pageable){

        var cards = cardsRepository.findAllByState(status, pageable);

        if(getUser().getRole().equals("admin")){
            return cardsRepository.findAllByState(status, pageable);
        }else {
            return cards.stream().filter(card -> card.getUser() == getUser()).collect(Collectors.toList());
        }

    }

    @Transactional
    public List<Card> getCardsByDate(Timestamp date, Pageable pageable){
        var cards = cardsRepository.findAllByCreated(date, pageable);

        if(getUser().getRole().equals("admin")){
            return cards;
        }else {
            return cards.stream().filter(card -> card.getUser() == getUser()).collect(Collectors.toList());
        }
    }

    @Transactional
    public List<Card> getCardsByName(String name, Pageable pageable){
        var cards = cardsRepository.findAllByName(name, pageable);

        if(getUser().getRole().equals("admin")){
            return cards;
        }else {
            return cards.stream().filter(card -> card.getUser() == getUser()).collect(Collectors.toList());
        }
    }

    @Transactional
    public Card saveNewCards(CardsDto cardsDto){
        return cardsRepository.save(convertDTOToCard(cardsDto,"To Do",getUser()));
    }

    @Transactional
    public Card updateCard(Card oldCard, CardsDto cardsDto){
        oldCard.setName(cardsDto.getName());
        oldCard.setDescription(cardsDto.getDescription());
        oldCard.setColour(cardsDto.getColour());
        return cardsRepository.save(oldCard);
    }

    private Users getUser(){
        var user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @Transactional
    public void deleteCard(Card card){
        cardsRepository.delete(card);
    }

    private Card convertDTOToCard(CardsDto cardsDto, String cardState,Users user){

        var card = new Card();
        card.setName(cardsDto.getName());
        card.setColour(card.getColour());
        card.setDescription(card.getDescription());
        card.setState(cardState);
        card.setUser(user);

        return card;
    }

}
