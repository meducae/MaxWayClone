package uz.gita.maxwayclone.data.sources.remote.model

data class BranchesResponse(
    val message: String,
    val data: List<BranchDto>
)

data class BranchDto(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String,
    val latitude: Double,
    val longitude: Double
)