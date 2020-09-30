package com.example.nexttodoapp.domain.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nexttodoapp.domain.models.dao.entity.Task

@Dao
interface TaskDao {

    //全部の取得
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    //個数の取得
    @Query("SELECT COUNT(*) FROM task")
    fun  getCount(): Int

    //追加
    @Insert
    fun insert(task:Task)

    //削除
    @Delete
    fun delete(task:Task)

}