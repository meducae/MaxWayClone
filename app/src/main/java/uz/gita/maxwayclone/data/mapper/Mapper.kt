package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.NotificationEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.NotificationItemResponse
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.NotificationModel

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
fun NotificationItemResponse.toEntity() = NotificationEntity(id, name, message, imgURL, sendDate)




fun NotificationEntity.toDomain() = NotificationModel(id, name, message, sendDate, imgURL)
