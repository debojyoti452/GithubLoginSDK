package com.swing.githubloginsdk.src.executor

import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor


class BackgroundExecutor {

    private var executorService: ExecutorService? = null
    private var handler: Handler? = null

    val numberOfCores = Runtime.getRuntime().availableProcessors()
    private val mForLightWeightBackgroundTasks: ThreadPoolExecutor? = null
    private val mMainThreadExecutor: Executor? = null

    fun <E> execute(requests: E) {

    }

    companion object {
        private var sInstance: BackgroundExecutor? = null

        fun getInstance(): BackgroundExecutor {
            if (sInstance == null) {
                sInstance = BackgroundExecutor()
            }
            return sInstance ?: BackgroundExecutor()
        }
    }
}
