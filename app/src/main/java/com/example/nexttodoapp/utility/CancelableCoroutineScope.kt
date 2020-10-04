package com.example.nexttodoapp.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CancelableCoroutineScope : CoroutineScope {

    //coroutineのJob
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun cancel() {
        job.cancel()
    }

}