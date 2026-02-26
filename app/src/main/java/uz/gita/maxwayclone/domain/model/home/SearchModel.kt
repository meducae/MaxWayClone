package uz.gita.maxwayclone.domain.model.home

data class SearchModel(
    val id: Int,

    val categoryId: Int,

    val name: String,

    val description: String,

    val image: String,

    val cost: Int
)