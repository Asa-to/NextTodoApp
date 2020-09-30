package com.example.nexttodoapp.domain.usecase

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.nexttodoapp.domain.models.TaskDb
import com.example.nexttodoapp.domain.models.dao.entity.Task


object RoomUseCase {

    private const val DB_NAME = "todo-list"

    sealed class RoomResult{
        class SuccessInsert(val taskName:String):RoomResult()
        class SuccessDelete(val position:Int):RoomResult()
        class SuccessData(val tasks:ArrayList<String>):RoomResult()
        object Failed:RoomResult()
    }

    //DBにinsert
    suspend fun insertRoom(taskName: String,context: Context):RoomResult{
        Log.d("Room","insert")
        try{
            //daoの生成
            val dao = Room.databaseBuilder(context,TaskDb::class.java, DB_NAME).build()
            val indexCount = dao.taskDao().getCount()
            dao.taskDao().insert(Task(id = indexCount+1,taskName = taskName))
            return RoomResult.SuccessInsert(taskName)
        }catch (t:Throwable){
            return RoomResult.Failed
        }
    }

    //DBの要素のdelete
    suspend fun deleteRoom(position:Int,taskName: String,context: Context):RoomResult{
        Log.d("Room","delete")
        try{
            val dao = Room.databaseBuilder(context,TaskDb::class.java, DB_NAME).build()
            dao.taskDao().delete(Task(id = position,taskName = taskName))
            return RoomResult.SuccessDelete(position)
        }catch (t:Throwable){
            return RoomResult.Failed
        }
    }

    suspend fun getRoom(context: Context):RoomResult{
        Log.d("Room","get")
        try{
            val dao = Room.databaseBuilder(context,TaskDb::class.java, DB_NAME).build()
            val tasks = arrayListOf<String>()
            dao.taskDao().getAll().forEach {
                tasks.add(it.taskName.toString())
            }
            return RoomResult.SuccessData(tasks)
        }catch (t:Throwable){
            return RoomResult.Failed
        }
    }
}