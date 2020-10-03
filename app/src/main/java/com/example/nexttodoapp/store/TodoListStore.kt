package com.example.nexttodoapp.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nexttodoapp.action.DbControlAction
import com.example.nexttodoapp.action.DbControlActionState
import com.example.nexttodoapp.dispatcher.Dispatcher
import com.example.nexttodoapp.domain.usecase.TodoItem

class TodoListStore(private val dispatcher: Dispatcher):ViewModel(),Dispatcher.ActionListener{

    init {
        dispatcher.register(this)
        Log.d("store","init")
    }


    val actionData: LiveData<DbControlActionState> = MutableLiveData<DbControlActionState>().apply { value = DbControlActionState.None }
    val todoList:ArrayList<TodoItem> = ArrayList<TodoItem>()

    private fun update(action: DbControlAction){
        Log.d("store","update")
        Log.d("store",action.toString())
        when(action.state){
            is DbControlActionState.SuccessInsert -> {
                Log.d("store","insertAt auto generated data ${action.state.todoItem.taskName}")
                todoList.add(action.state.todoItem)
            }
            is DbControlActionState.SuccessDelete -> {
                Log.d("store","deleteAt ${action.state.todoItem.id} data ${action.state.todoItem.taskName}")
                todoList.removeAt(todoList.indexOf(action.state.todoItem))
            }
            is DbControlActionState.SuccessData -> {
                todoList.clear()
                todoList.addAll(action.state.tasks)
            }
        }
        (actionData as MutableLiveData<DbControlActionState>).value = action.state
        Log.d("store",todoList.toString())
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