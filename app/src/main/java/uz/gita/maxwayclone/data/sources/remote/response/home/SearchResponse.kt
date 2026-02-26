package uz.gita.maxwayclone.data.sources.remote.response.home

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<SearchItemResponse>
)


data class SearchItemResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("categoryID")
    val categoryId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("cost")
    val cost: Int
)