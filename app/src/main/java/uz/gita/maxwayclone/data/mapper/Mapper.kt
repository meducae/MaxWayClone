package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.AdsEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.domain.model.home.AdsModel

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
