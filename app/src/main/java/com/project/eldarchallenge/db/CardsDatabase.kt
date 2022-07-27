package com.project.eldarchallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cards::class], version = 1, exportSchema = false)
abstract class CardsDatabase: RoomDatabase() {

    abstract fun cardsDao(): CardsDao

    companion object{
        @Volatile
        private var INSTANCE: CardsDatabase? = null

        fun getDatabase(context: Context):CardsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardsDatabase::class.java,
                    "cards_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}