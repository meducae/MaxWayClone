package uz.gita.maxwayclone.domain.model.home

sealed class ProductTypeModel {
    data class CategoryHeader(
        val categoryId: Int,
        val categoryName: String
    ) : ProductTypeModel()

    data class ProductItem(
        val product: ProductModel, val count: Int = 0
    ) : ProductTypeModel()
}