package uz.gita.maxwayclone.domain.model.home

import java.io.Serializable

data class SearchModel(
    val id: Int,

    val categoryId: Int,

    val name: String,

    val description: String,

    val image: String,

    val cost: Int
): Serializable