package com.swing.githubloginsdk

import android.app.Activity
import com.swing.githubloginsdk.src.model.AuthResult
import com.swing.githubloginsdk.src.ui.GithubSDK


class GithubAuth private constructor(
    private var gitClientId: String,
    private var gitSecret: String,
    val onSuccess: ((authResult: AuthResult) -> Unit),
    val onFailed: ((exception: Exception) -> Unit),
    val activity: Activity,
) {
    private lateinit var githubSDK: GithubSDK

    open class Builder(
        private val gitClientId: String,
        private val gitSecret: String,
    ) {
        private lateinit var onSuccess: ((authResult: AuthResult) -> Unit)
        private lateinit var onFailed: ((error: Exception) -> Unit)
        private var activity: Activity? = null

        fun setActivity(activity: Activity): Builder {
            this.activity = activity
            return this
        }

        fun setOnSuccess(onSuccess: (authResult: AuthResult) -> Unit): Builder {
            this.onSuccess = onSuccess
            return this
        }

        fun setOnFailed(onFailed: (error: Exception) -> Unit): Builder {
            this.onFailed = onFailed
            return this
        }

        fun build(): GithubAuth {
            when (activity) {
                null -> {
                    throw NullPointerException(
                        "Activity is missing. " +
                                "You need to pass current activity. " +
                                "Use setActivity(activity: Activity) function."
                    )
                }
                else -> {
                    return GithubAuth(
                        gitClientId = gitClientId,
                        gitSecret = gitSecret,
                        onSuccess = onSuccess,
                        onFailed = onFailed,
                        activity = activity!!,
                    ).init()
                }
            }
        }
    }

    fun auth() {
        githubSDK.callGithubAuth()
    }

    fun onDeepLinkInitializer() {
        githubSDK.checkDeepLinkData()
    }

    private fun init(): GithubAuth {
        githubSDK = GithubSDK.newInstance(
            activity,
            gitClientId = gitClientId,
            gitSecret = gitSecret,
            onSuccess = onSuccess,
            onFailed = onFailed
        )
        return this
    }
}
