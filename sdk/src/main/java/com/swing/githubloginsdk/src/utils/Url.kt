package com.swing.githubloginsdk.src.utils

internal object Url {
    const val accessUrl = "https://github.com/login/oauth/access_token"
    const val baseUrl = "https://github.com/login/oauth/authorize"
    const val clientId = "client_id"
    const val scope = "scope=public_repo"
    const val read = "read:user"
    const val user = "user:email"

    const val CONTENT_TYPE_JSON = "application/json"
    const val HTTP_POST = "POST"
    const val TIMEOUT_CONNECT = 5000
    const val TIMEOUT_READ = 30000
    const val HEADER_ACCEPT = "Accept"
    const val HEADER_CONTENT_TYPE = "Content-Type"
}
