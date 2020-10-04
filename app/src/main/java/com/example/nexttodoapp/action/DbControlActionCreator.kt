package com.example.nexttodoapp.action

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.nexttodoapp.dispatcher.Dispatcher
import com.example.nexttodoapp.domain.usecase.RoomUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class ActionMode {
    GET, INSERT, DELETE
}

class DbControlActionCreator(private val dispatcher:Dispatcher,private val coroutineScope: CoroutineScope) {

    private val TAG = "ActionCreator"

    fun execute(mode:ActionMode, id : Long?,taskName:String?,context: Context){
        coroutineScope.launch(Dispatchers.Default){
            createAction(mode, id, taskName,context)
            Log.d(TAG,"execute launch createAction")
        }
    }

    private suspend fun createAction(mode:ActionMode,id:Long?,taskName: String?,context:Context){
        //modeでactionの制御
        Log.d("action create","mode = ${mode.toString()}")

        val result:RoomUseCase.RoomResult = when(mode){
            ActionMode.INSERT -> RoomUseCase().insertRoom(taskName!!,context)
            ActionMode.DELETE -> RoomUseCase().deleteRoom(id!!,taskName!!,context)
            ActionMode.GET ->  RoomUseCase().getRoom(context)
        }

        Log.d("action create",result.toString())
        //resultStateの格納
        val resultState = when(result){
            is RoomUseCase.RoomResult.SuccessInsert -> DbControlActionState.SuccessInsert(result.todoItem)
            is RoomUseCase.RoomResult.SuccessDelete -> DbControlActionState.SuccessDelete(result.todoItem)
            is RoomUseCase.RoomResult.SuccessData -> DbControlActionState.SuccessData(result.tasks)
            is RoomUseCase.RoomResult.Failed -> DbControlActionState.Failed
        }

        Log.d("action create",resultState.toString())

        //dispatchにactionを送る
        coroutineScope.launch(Dispatchers.Main) {
            dispatcher.dispatch(DbControlAction(resultState))
            Log.d(TAG,"dispatch")
        }
    }
}