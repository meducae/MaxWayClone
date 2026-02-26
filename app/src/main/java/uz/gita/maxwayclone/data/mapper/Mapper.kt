package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.SearchEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.SearchItemResponse
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.SearchModel

fun AdItemResponse.toEntity() = AdsEntity(id = id ?: 0, imageUrl = bannerUrl ?: "")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
fun SearchItemResponse.toEntity() = SearchEntity(
    id = id ?: 0,
    categoryId = categoryId ?: 0,
    name = name ?: "",
    description = description ?: "",
    image = image ?: "",
    cost = cost ?: 0
)

fun SearchEntity.toDomain() = SearchModel(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    image = image,
    cost = cost
)

