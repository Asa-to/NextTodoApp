package com.example.nexttodoapp.dispatcher

import android.util.Log
import com.example.nexttodoapp.action.DbControlAction
import java.util.Collections

class Dispatcher {

    private val listeners = Collections.synchronizedList(mutableListOf<ActionListener>())

    fun dispatch(action: DbControlAction){
        listeners.forEach{it.on(action)}
        Log.d("dispatcher","on")
    }

    fun register(listener: ActionListener) {
        listeners.add(listener)
    }

    fun unregister(listener: ActionListener) {
        listeners.remove(listener)
    }

    interface ActionListener{
        fun on(action: DbControlAction)
    }

}