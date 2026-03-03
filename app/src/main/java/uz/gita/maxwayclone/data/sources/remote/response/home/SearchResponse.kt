package uz.gita.maxwayclone.data.sources.remote.response.home

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("message")
    val message: String?=null,

    @SerializedName("data")
    val data: List<SearchItemResponse>?=null
)


data class SearchItemResponse(
    @SerializedName("id")
    val id: Int?=null,

    @SerializedName("categoryID")
    val categoryId: Int?=null,

    @SerializedName("name")
    val name: String?=null,

    @SerializedName("description")
    val description: String?=null,

    @SerializedName("image")
    val image: String?=null,

    @SerializedName("cost")
    val cost: Int?=null
)