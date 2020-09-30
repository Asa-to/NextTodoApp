package com.example.nexttodoapp.action

sealed class DbControlActionState {
    object None:DbControlActionState()
    data class SuccessInsert(val taskName:String):DbControlActionState()
    data class SuccessDelete(val position:Int):DbControlActionState()
    data class SuccessData(val tasks:ArrayList<String>):DbControlActionState()
    object Failed:DbControlActionState()
}