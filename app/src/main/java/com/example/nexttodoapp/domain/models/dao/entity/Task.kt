package com.example.nexttodoapp.domain.models.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "task-name") val taskName:String?
)