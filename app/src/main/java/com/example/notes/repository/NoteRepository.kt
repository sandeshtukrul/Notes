package com.example.notes.repository

import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun deleteById(id: Int)

    fun getNotes(): Flow<List<Note>>

    fun getNote(id: Int): Flow<Note>

}