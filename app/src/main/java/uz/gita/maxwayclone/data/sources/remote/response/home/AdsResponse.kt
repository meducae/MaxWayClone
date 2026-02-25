package uz.gita.maxwayclone.data.sources.remote.response.home

data class AdsResponse(
    val message: String ?=null,
    val data: List<AdItemResponse>?=null
)

data class AdItemResponse(
    val id: Int?=null,
    val bannerUrl: String?=null
)

data class AdsStoriesResponse(
    val message: String?=null,
    val data : List<AdsStoriesItemResponse>
)

data class AdsStoriesItemResponse(
    val id : Int,
    val name : String,
    val url : String
)
