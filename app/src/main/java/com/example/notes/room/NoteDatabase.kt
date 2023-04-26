package com.example.notes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
}