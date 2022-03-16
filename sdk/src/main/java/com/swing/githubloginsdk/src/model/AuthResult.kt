package com.swing.githubloginsdk.src.model

import com.google.gson.annotations.SerializedName

data class AuthResult(

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("tokenType")
	val tokenType: String? = null
)
