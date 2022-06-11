package com.dhandev.recam.networking.data.response

import com.google.gson.annotations.SerializedName

data class ResponsePaper(

	@field:SerializedName("ResponsePaper")
	val responsePaper: List<ResponsePaperItem>
)

data class ResponsePaperItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("diskripsi")
	val diskripsi: String,

	@field:SerializedName("judul")
	val judul: String
)
