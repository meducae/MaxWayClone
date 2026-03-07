package uz.gita.maxwayclone.data.sources.remote.request.register

data class VerifyRequest(
    val phone: String,
    val code: Int
)