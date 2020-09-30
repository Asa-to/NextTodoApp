package com.example.nexttodoapp.domain.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nexttodoapp.domain.models.dao.entity.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT COUNT(*) FROM task")
    fun  getCount(): Int

    @Insert
    fun insert(task:Task)

    @Delete
    fun delete(task:Task)

}