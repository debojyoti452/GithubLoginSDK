package com.swing.githubloginsdk

import android.app.Activity
import android.content.Context
import com.swing.githubloginsdk.src.ui.GithubSDK
import kotlin.properties.Delegates


class GithubAuth private constructor(
    private var isSafeWindow: Boolean,
    private var gitClientId: String,
    private var gitSecret: String,
    val onSuccess: ((token: String) -> Unit),
    val onFailed: ((error: String) -> Unit),
    val context: Context,
    val activity: Activity,
) {
    private lateinit var githubSDK: GithubSDK

    open class Builder(
        private val gitClientId: String,
        private val gitSecret: String,
    ) {
        private var isSafeWindow by Delegates.notNull<Boolean>()
        private lateinit var onSuccess: ((token: String) -> Unit)
        private lateinit var onFailed: ((error: String) -> Unit)
        private var context: Context? = null
        private var activity: Activity? = null

        fun setContext(context: Context): Builder {
            this.context = context
            return this
        }

        fun setActivity(activity: Activity): Builder {
            this.activity = activity
            return this
        }

        fun setIsSafeWindow(isSafeWindow: Boolean = true): Builder {
            this.isSafeWindow = isSafeWindow
            return this
        }

        fun setOnSuccess(onSuccess: (token: String) -> Unit): Builder {
            this.onSuccess = onSuccess
            return this
        }

        fun setOnFailed(onFailed: (error: String) -> Unit): Builder {
            this.onFailed = onFailed
            return this
        }

        fun build(): GithubAuth {
            when {
                context == null -> {
                    throw NullPointerException(
                        "Context is missing. " +
                                "You need to pass current context. " +
                                "Use setContext(context: Context) function."
                    )
                }
                activity == null -> {
                    throw NullPointerException(
                        "Activity is missing. " +
                                "You need to pass current activity. " +
                                "Use setActivity(activity: Activity) function."
                    )
                }
                else -> {
                    return GithubAuth(
                        isSafeWindow = isSafeWindow,
                        gitClientId = gitClientId,
                        gitSecret = gitSecret,
                        onSuccess = onSuccess,
                        onFailed = onFailed,
                        context = context!!,
                        activity = activity!!,
                    ).init()
                }
            }
        }
    }

    fun auth() {
        githubSDK.callGithubAuth()
    }

    fun initialize() {
        githubSDK.checkDeepLinkData()
    }

    private fun init(): GithubAuth {
        githubSDK = GithubSDK.newInstance(activity, gitClientId = gitClientId, gitSecret = gitSecret)
        return this
    }
}
