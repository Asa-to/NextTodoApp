package com.example.nexttodoapp.domain.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nexttodoapp.domain.models.dao.TaskDao
import com.example.nexttodoapp.domain.models.dao.entity.Task

@Database(entities = [Task::class],version = 1)
abstract class TaskDb:RoomDatabase(){
    abstract fun taskDao():TaskDao

    companion object {
        @Volatile private var INSTANCE: TaskDb? = null

        fun getDatabase(context: Context): TaskDb {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDb::class.java, "word_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}