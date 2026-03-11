package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetBasketTotalPriceUseCase {
    operator fun invoke(): Flow<Long>
}