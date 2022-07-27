package com.project.eldarchallenge.db

import androidx.lifecycle.LiveData

class CardsRepository(private val cardsDao: CardsDao) {

    var readCardsData: LiveData<List<Cards>> = cardsDao.readCardsData()

    suspend fun addCards(cards: Cards){
        cardsDao.addCards(cards)
    }
}