package com.dhandev.recam.networking.data.response

import com.google.gson.annotations.SerializedName

data class ResponseTest(

	@field:SerializedName("ResponseTest")
	val responseTest: List<ResponseTestItem>
)

data class ResponseTestItem(

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("pubDate")
	val pubDate: String
)
