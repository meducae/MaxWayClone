package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.maxwayclone.data.sources.remote.model.BranchesResponse

interface BranchApi {
    @GET("branches")
    suspend fun getBranches(): Response<BranchesResponse>
}
// filiallar