package com.project.eldarchallenge.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_table")
data class Cards(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val lastName: String,
    val brand: String,
    val number: String,
    val securityCode: String,
    val expirationDate: String
)