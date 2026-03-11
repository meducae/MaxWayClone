package uz.gita.maxwayclone.presentation.branch.ui_state

import uz.gita.maxwayclone.domain.model.branch.Branch

sealed class BranchUiState {
    object Loading : BranchUiState()
    data class Success(val data: List<Branch>) : BranchUiState()
    data class Error(val message: String) : BranchUiState()
}