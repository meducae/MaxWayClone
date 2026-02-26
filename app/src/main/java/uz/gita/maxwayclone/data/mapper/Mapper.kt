package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.remote.model.BranchDto
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.domain.model.branch.Branch
import uz.gita.maxwayclone.domain.model.home.AdsModel

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)

fun BranchDto.toDomain() = Branch(
    id = id,
    name = name,
    address = address,
    phone = phone,
    openTime = openTime,
    closeTime = closeTime
)