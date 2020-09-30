package com.example.nexttodoapp.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nexttodoapp.action.DbControlAction
import com.example.nexttodoapp.action.DbControlActionState
import com.example.nexttodoapp.dispatcher.Dispatcher

class TodoListStore(private val dispatcher: Dispatcher):ViewModel(),Dispatcher.ActionListener{

    init {
        dispatcher.register(this)
        Log.d("store","init")
    }

    val actionData: LiveData<DbControlActionState> = MutableLiveData<DbControlActionState>().apply { value = DbControlActionState.None }
    val todoList:ArrayList<String> = ArrayList()

    private fun update(action: DbControlAction){
        Log.d("store","update")
        (actionData as MutableLiveData<DbControlActionState>).value = action.state
        when(action.state){
            is DbControlActionState.SuccessInsert -> todoList.add(action.state.taskName)
            is DbControlActionState.SuccessDelete -> todoList.removeAt(action.state.position)
            is DbControlActionState.SuccessData -> {
                todoList.clear()
                todoList.addAll(action.state.tasks)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispatcher.unregister(this)
    }

    override fun on(action: DbControlAction) {
        Log.d("store","on")
        update(action)
    }
}