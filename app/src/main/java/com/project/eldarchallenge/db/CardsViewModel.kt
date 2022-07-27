package com.project.eldarchallenge.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsViewModel(application: Application): AndroidViewModel(application) {

    var readCardsData: LiveData<List<Cards>>
    private val repository: CardsRepository

    init {
        val cardsDao = CardsDatabase.getDatabase(application).cardsDao()
        repository = CardsRepository(cardsDao)
        readCardsData = repository.readCardsData
    }

    fun addCard(cards: Cards){
        viewModelScope.launch(Dispatchers.IO){
            repository.addCards(cards)
        }
    }
}