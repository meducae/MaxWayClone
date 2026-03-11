package uz.gita.maxwayclone

import java.text.NumberFormat
import java.util.Locale

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    fun Long.formatPrice(): String {
        val formatter = NumberFormat.getInstance(Locale("ru", "RU"))
        formatter.isGroupingUsed = true
        return formatter.format(this)
    }
    fun Int.formatPrice(): String {
        val formatter = NumberFormat.getInstance(Locale("ru", "RU"))
        formatter.isGroupingUsed = true
        return formatter.format(this)
    }
}