package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.order.my_order.MyData
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)

fun MyData.toUIData(): MyOrdersUIData {
    return MyOrdersUIData(
        id = id,
        address = address,
        createTime = createTime,
        latitude = latitude,
        longitude = longitude,
        ls = ls,
        sum = sum,
        userID = userID,
        currentStage = 1,
        orderNumber = "100",
        statusText = "Yaratildi"
    )
}
