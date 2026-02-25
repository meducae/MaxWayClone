package uz.gita.maxwayclone.data.sources.remote.response.home

import com.google.gson.annotations.SerializedName

data class NotificationResponse (
    @SerializedName("message")
    val massage: String,
    val data: List<NotificationItemResponse>

)
data class NotificationItemResponse(
    val id: Int,
    val name: String,
    val message: String,
    val imgURL: String,
    val sendDate: String
)