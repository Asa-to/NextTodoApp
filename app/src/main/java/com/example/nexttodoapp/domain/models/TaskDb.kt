package com.example.nexttodoapp.domain.models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nexttodoapp.domain.models.dao.TaskDao
import com.example.nexttodoapp.domain.models.dao.entity.Task

@Database(entities = [Task::class],version = 1)
abstract class TaskDb:RoomDatabase(){
    abstract fun taskDao():TaskDao
}