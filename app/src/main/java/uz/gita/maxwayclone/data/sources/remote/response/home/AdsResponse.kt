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

data class GeneralResponse<T>(
    val message : String,
    val data : List<T>
)


data class CategoriesResponse(
    val id : Int,
    val name : String
)
data class ProductsResponse(
    val id : Int,
    val name : String,
    val products : List<ProductResponse>
)


data class ProductResponse(
    val id : Int,
    val categoryID : Int,
    val name : String,
    val description : String,
    val image : String,
    val cost : Int
)
