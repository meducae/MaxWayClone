package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.request.register.RepeatRequest
import uz.gita.maxwayclone.data.sources.remote.response.repeat.ResponseRepeat

interface RepeatUseCase {
    operator fun invoke(request: RepeatRequest): Flow<Result<ResponseRepeat>>
}