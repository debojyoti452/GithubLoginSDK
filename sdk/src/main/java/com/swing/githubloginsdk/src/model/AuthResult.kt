package com.swing.githubloginsdk.src.model

import com.google.gson.annotations.SerializedName

data class AuthResult(

    @field:SerializedName("scope")
    val scope: String? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("error_description")
    val errorDescription: String? = null,

    @field:SerializedName("error")
    val error: String? = null
)
