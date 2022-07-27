package com.project.eldarchallenge.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCards(cards: Cards)

    @Query("SELECT * FROM cards_table ORDER BY id ASC")
    fun readCardsData(): LiveData<List<Cards>>
}