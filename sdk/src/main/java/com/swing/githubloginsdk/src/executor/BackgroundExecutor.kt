package com.swing.githubloginsdk.src.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


internal class BackgroundExecutor constructor(
    private var executorService: ExecutorService = Executors.newCachedThreadPool(),
    private var handler: Handler = Handler(Looper.getMainLooper())
) {

    fun <E> execute(functions: () -> Unit) {
        executorService.submit {
            functions()
        }
    }

    fun getUiThread(): Handler {
        return handler
    }
}
