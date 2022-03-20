package com.swing.githubloginsdk.src.utils

object Url {
//    const val baseUrl =
//        "https://github.com/login/oauth/authorize?client_id=31e1daafe57abcbd91ce&scope=public_repo%20read:user%20user:email"

    const val baseUrl = "https://github.com/login/oauth/authorize?"
    const val clientId = "client_id"
    const val scope = "scope"
    const val read = "read:user"
    const val user = "user:email"

    const val CONTENT_TYPE_JSON = "application/json"
    const val HTTP_POST = "POST"
    const val TIMEOUT_CONNECT = 5000
    const val TIMEOUT_READ = 30000
    const val HEADER_USER_AGENT = "User-Agent"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_ACCEPT = "Accept"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val VALUE_APP_JSON = "application/json;charset=UTF-8"
}
