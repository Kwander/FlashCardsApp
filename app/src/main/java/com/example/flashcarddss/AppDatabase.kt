package com.example.flashcarddss

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcarddss.Flashcard
import com.example.flashcarddss.FlashcardDao

@Database(entities = [Flashcard::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
}
