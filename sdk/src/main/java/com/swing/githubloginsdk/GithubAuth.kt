package com.swing.githubloginsdk

import android.content.Context
import android.content.Intent
import com.swing.githubloginsdk.src.ui.GithubSheetFragment
import kotlin.properties.Delegates

class GithubAuth private constructor(
    private var isSafeWindow: Boolean,
    private var gitSecret: String,
    private var gitToken: String,
    val onSuccess: ((token: String) -> Unit),
    val onFailed: ((error: String) -> Unit),
    val context: Context,
) {

    open class Builder(
        private val gitSecret: String,
        private val gitToken: String,
    ) {
        private var isSafeWindow by Delegates.notNull<Boolean>()
        private lateinit var onSuccess: ((token: String) -> Unit)
        private lateinit var onFailed: ((error: String) -> Unit)
        private var context: Context? = null

        fun setContext(context: Context): Builder {
            this.context = context
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
            if (context == null) {
                throw NullPointerException(
                    "Activity is missing. " +
                            "You need to pass current activity. " +
                            "Use setActivity(activity: Activity) function."
                )
            } else {
                return GithubAuth(
                    isSafeWindow = isSafeWindow,
                    gitSecret = gitSecret,
                    gitToken = gitToken,
                    onSuccess = onSuccess,
                    onFailed = onFailed,
                    context = context!!,
                )
            }
        }
    }

    fun auth() {
//        https://github.com/login/oauth/authorize?client_id=31e1daafe57abcbd91ce&scope=public_repo%20read:user%20user:email
//        onSuccess.invoke("Hello World")
//        val dialog = GithubSheetFragment.newInstance()
//        dialog.show((context as FragmentActivity).supportFragmentManager, GithubAuth::class.java.simpleName)
        context.startActivity(Intent(context, GithubSheetFragment::class.java))
    }
}
