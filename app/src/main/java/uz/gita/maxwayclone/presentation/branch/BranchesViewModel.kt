package uz.gita.maxwayclone.presentation.branch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.domain.usecase.GetBranchesUseCase
import uz.gita.maxwayclone.presentation.branch.ui_state.BranchUiState

class BranchesViewModel(
    private val getBranchesUseCase: GetBranchesUseCase
) : ViewModel() {

    private val _branches = MutableStateFlow<BranchUiState>(BranchUiState.Loading)
    val branches = _branches.asStateFlow()

    fun getBranches() = viewModelScope.launch {

        _branches.value = BranchUiState.Loading

        getBranchesUseCase().collect { result ->
            result.onSuccess {
                _branches.value = BranchUiState.Success(it)
            }
            result.onFailure {
                _branches.value = BranchUiState.Error(it.message ?: "Error")
            }
        }
    }
}