package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.StoriesEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdStoriesResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdStoriesResponseData
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

fun AdItemResponse.toEntity() = AdsEntity(id = id, imageUrl = bannerUrl)
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
fun AdStoriesResponseData.toEntity()  = StoriesEntity(id = id , name = name , url = url)
fun StoriesEntity.toData() = StoriesModel(id = id , name , url)

