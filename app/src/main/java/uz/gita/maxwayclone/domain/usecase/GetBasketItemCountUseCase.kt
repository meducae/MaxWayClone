package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetBasketItemCountUseCase {
    operator fun invoke() : Flow<Int>
}