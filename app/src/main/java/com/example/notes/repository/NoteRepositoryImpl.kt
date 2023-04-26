package com.example.notes.repository

import com.example.notes.model.Note
import com.example.notes.room.NoteDao
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun update(note: Note) {
        noteDao.update(note)
    }

    override suspend fun deleteById(id: Int) {
        noteDao.deleteById(id)
    }

    override fun getNotes() = noteDao.getNotes()

    override fun getNote(id: Int) = noteDao.getNote(id)

}