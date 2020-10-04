package com.example.nexttodoapp.domain.models.dao

import androidx.room.*
import com.example.nexttodoapp.domain.models.dao.entity.Task

@Dao
interface TaskDao {

    //全部の取得
    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    //個数の取得
    @Query("SELECT COUNT(*) FROM tasks")
    fun  getCount(): Int

    //追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task:Task):Long

    //削除
    @Delete
    fun delete(task:Task)

    @Query("DELETE FROM tasks WHERE id=:id")
    fun deleteAt(id: Long)

    @Query("SELECT id FROM tasks WHERE `task-name`=:task")
    fun find(task:String):Long

}