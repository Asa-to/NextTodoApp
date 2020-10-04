package com.example.nexttodoapp.domain.usecase

import android.content.Context
import android.util.Log
import com.example.nexttodoapp.domain.models.TaskDb
import com.example.nexttodoapp.domain.models.dao.entity.Task


class RoomUseCase {

    sealed class RoomResult {
        class SuccessInsert(val todoItem: TodoItem) : RoomResult()
        class SuccessDelete(val todoItem: TodoItem) : RoomResult()
        class SuccessData(val tasks: ArrayList<TodoItem>) : RoomResult()
        object Failed : RoomResult()
    }

    //DBにinsert
    suspend fun insertRoom(taskName: String,context: Context): RoomResult {
        Log.d("Room", "insert")
        try {
            //daoの生成
            val dao = TaskDb.getDatabase(context).taskDao()
            val id = dao.insert(Task(id = 0,taskName = taskName))
            val taskId = dao.find(taskName)
            return RoomResult.SuccessInsert(TodoItem(id = id, taskName = taskName))
        } catch (t: Throwable) {
            return RoomResult.Failed
        }
    }

    //DBの要素のdelete
    suspend fun deleteRoom(id: Long, taskName: String,context: Context): RoomResult {
        Log.d("Room", "delete")
        try {
            val dao = TaskDb.getDatabase(context).taskDao()
            dao.deleteAt(id = id)
            return RoomResult.SuccessDelete(TodoItem(id = id, taskName = taskName))
        } catch (t: Throwable) {
            return RoomResult.Failed
        }
    }

    suspend fun getRoom(context: Context): RoomResult {
        Log.d("Room", "get")
        try {
            val tasks = arrayListOf<TodoItem>()
            val dao = TaskDb.getDatabase(context).taskDao()
            dao.getAll().forEach {
                val item = TodoItem(id = it.id, taskName = it.taskName!!)
                tasks.add(item)
            }
            return RoomResult.SuccessData(tasks)
        } catch (t: Throwable) {
            return RoomResult.Failed
        }
    }

}