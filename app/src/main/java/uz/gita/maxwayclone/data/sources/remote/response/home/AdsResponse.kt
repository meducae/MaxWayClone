package uz.gita.maxwayclone.data.sources.remote.response.home

data class AdsResponse(
    val message: String,
    val data: List<AdItemResponse>
)

data class AdItemResponse(
    val id: Int,
    val bannerUrl: String
)

data class AdStoriesResponse(
    val message : String,
    val data : List<AdStoriesResponseData>
)

data class AdStoriesResponseData(
    val id : Int,
    val name: String,
    val url : String
)