package com.swing.githubloginsdk.src.utils

internal object Utils {
    fun scopeUrlGenerator(scopeList: List<String>, redirectUri: String? = null): String {
        val scopeUrlBuilder = StringBuilder()
            .append("&scope=")

        scopeList.forEach {
            scopeUrlBuilder
                .append(it)
                .append("&")
        }

        if (redirectUri != null) {
            scopeUrlBuilder.append("redirect_uri=")
                .append(redirectUri)
        }

        return scopeUrlBuilder.removeSuffix("&").toString()
    }
}
