package uz.gita.maxwayclone.data.mapper

import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.BasketEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.CategoriesEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.ProductsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.StoriesEntity
import uz.gita.maxwayclone.data.sources.remote.model.BranchDto
import uz.gita.maxwayclone.data.sources.remote.model.GetBasketItemOrder
import uz.gita.maxwayclone.data.sources.remote.request.CreateOrder
import uz.gita.maxwayclone.data.sources.remote.request.OrderItem
import uz.gita.maxwayclone.data.sources.local.room.entity.SearchEntity
import uz.gita.maxwayclone.data.sources.remote.response.home.AdItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsStoriesItemResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.CategoriesResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.ProductResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.ProductsResponse
import uz.gita.maxwayclone.domain.model.branch.Branch
import uz.gita.maxwayclone.data.sources.remote.response.home.SearchItemResponse
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import kotlin.Int
import uz.gita.maxwayclone.domain.model.home.SearchModel

fun AdItemResponse.toEntity() = AdsEntity(id = id?:0, imageUrl = bannerUrl?:"")
fun AdsEntity.toDomain() = AdsModel(id = id, imageUrl = imageUrl)
fun StoriesEntity.toDomain() = StoriesModel(id = id , name = name , imageUrl = url)
fun StoriesModel.toEntity() = StoriesEntity(id=id , name = name , url = imageUrl)
fun AdsStoriesItemResponse.toEntity() = StoriesEntity(id=id , name = name , url = url)
fun ProductsEntity.toModel() = ProductModel(id , categoryId , categoryName , name , description , image = imgUrl , cost = cost)
fun CategoriesResponse.toEntity() = CategoriesEntity(id = id , name = name)
fun CategoriesEntity.toModel() = CategoryModel(id = id , name = name)
fun GetBasketItemOrder.toRequest() = OrderItem(productID = productId , count = count)
fun List<ProductModel>.toTypeModel(basketItems: List<BasketModel> = emptyList()): List<ProductTypeModel> {
    val result = mutableListOf<ProductTypeModel>()
    this
        .groupBy { it.categoryID to it.categoryName }
        .forEach { (category, products) ->

            val (categoryId, categoryName) = category

            result.add(
                ProductTypeModel.CategoryHeader(categoryId, categoryName)
            )

            products.forEach { product ->
                val basketItem = basketItems.find { it.productId == product.id }
                val count = basketItem?.count ?: 0
                result.add(ProductTypeModel.ProductItem(product, count))
            }
        }
    return result
}
fun SearchItemResponse.toEntity() = SearchEntity(
    id = id ?: 0,
    categoryId = categoryId ?: 0,
    name = name ?: "",
    description = description ?: "",
    image = image ?: "",
    cost = cost ?: 0
)

fun SearchEntity.toDomain() = SearchModel(
    id = id ?: 0,
    categoryId = categoryId ?: 0,
    name = name ?: "",
    description = description ?: "",
    image = image ?: "",
    cost = cost ?: 0
)


fun BasketEntity.toModel() = BasketModel(name = name , productId = productId , imageUrl = imageUrl , count = count , cost =  cost , description = description)
fun ProductResponse.toModel() = RcProductModel(name = name , categoryID = categoryID , id = id , image = image , cost = cost , description = description )

fun BranchDto.toDomain() = Branch(
    id = id,
    name = name,
    address = address,
    phone = phone,
    openTime = openTime,
    closeTime = closeTime
)