package com.example.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    val title: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
)
