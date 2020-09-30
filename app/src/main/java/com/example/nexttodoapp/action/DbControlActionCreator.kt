package com.example.nexttodoapp.action

import android.content.Context
import android.util.Log
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

    fun execute(mode:ActionMode,position: Int?,taskName:String?,context: Context){
        coroutineScope.launch(Dispatchers.Default){
            createAction(mode, position, taskName, context)
            Log.d(TAG,"execute launch createAction")
        }
    }

    private suspend fun createAction(mode:ActionMode,position:Int?,taskName: String?,context: Context){
        //modeでactionの制御
        val result = when(mode){
            ActionMode.INSERT -> taskName?.let { RoomUseCase.insertRoom(it, context) }
            ActionMode.DELETE -> taskName?.let { position?.let { it1 -> RoomUseCase.deleteRoom(it1, it, context)} }
            ActionMode.GET ->  RoomUseCase.getRoom(context)
        }
        //resultStateの格納
        val resultState = when(result){
            is RoomUseCase.RoomResult.SuccessInsert -> DbControlActionState.SuccessInsert(result.taskName)
            is RoomUseCase.RoomResult.SuccessDelete -> DbControlActionState.SuccessDelete(result.position)
            is RoomUseCase.RoomResult.SuccessData -> DbControlActionState.SuccessData(result.tasks)
            is RoomUseCase.RoomResult.Failed -> DbControlActionState.Failed
            else -> null
        }

        //dispatchにactionを送る
        coroutineScope.launch(Dispatchers.Main) {
            resultState?.let { dispatcher.dispatch(DbControlAction(it)) }
            Log.d(TAG,"dispatch")
        }
    }
}