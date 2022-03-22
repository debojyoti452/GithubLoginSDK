package com.swing.githubloginsdk.src.utils

internal object Utils {
    fun scopeUrlGenerator(scopeList: List<String>): String {
        val scopeUrlBuilder = StringBuilder()
            .append("&scope=")

        scopeList.forEach {
            scopeUrlBuilder
                .append(it)
                .append("&")
        }

        return scopeUrlBuilder.removeSuffix("&").toString()
    }
}
