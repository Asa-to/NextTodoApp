package com.example.nexttodoapp.action

import com.example.nexttodoapp.domain.usecase.TodoItem

sealed class DbControlActionState {
    object None:DbControlActionState()
    data class SuccessInsert(val todoItem: TodoItem):DbControlActionState()
    data class SuccessDelete(val todoItem: TodoItem):DbControlActionState()
    data class SuccessData(val tasks:ArrayList<TodoItem>):DbControlActionState()
    object Failed:DbControlActionState()
}