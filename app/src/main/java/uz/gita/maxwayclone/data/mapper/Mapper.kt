package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.StoriesEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsStoriesItemResponse
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
fun StoriesEntity.toDomain() = StoriesModel(id = id , name = name , imageUrl = url)
fun StoriesModel.toEntity() = StoriesEntity(id=id , name = name , url = imageUrl)
fun AdsStoriesItemResponse.toEntity() = StoriesEntity(id=id , name = name , url = url)

