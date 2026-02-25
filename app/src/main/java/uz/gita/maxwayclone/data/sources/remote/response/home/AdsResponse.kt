package uz.gita.maxwayclone.data.sources.remote.response.home

data class AdsResponse(
    val message: String ?=null,
    val data: List<AdItemResponse>?=null
)

data class AdItemResponse(
    val id: Int?=null,
    val bannerUrl: String?=null
)