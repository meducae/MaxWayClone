package uz.gita.maxwayclone.domain.model.branch

data class Branch(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String
)