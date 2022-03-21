package com.swing.githubloginsdk.src.model

import com.google.gson.annotations.SerializedName

data class GitRequest(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("client_secret")
	val clientSecret: String? = null,

	@field:SerializedName("client_id")
	val clientId: String? = null
)
