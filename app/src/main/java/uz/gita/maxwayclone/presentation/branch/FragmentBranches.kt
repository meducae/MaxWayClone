package uz.gita.maxwayclone.presentation.branch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.databinding.FragmentBranchesBinding
import uz.gita.maxwayclone.presentation.adapter.BranchAdapter
import uz.gita.maxwayclone.presentation.branch.ui_state.BranchUiState

class FragmentBranches : Fragment(R.layout.fragment_branches) {

    private val binding by viewBinding(FragmentBranchesBinding::bind)

    private val viewModel: BranchesViewModel by viewModels { BranchesViewModelFactory(ApiClient.getBranchApi()) }

    private lateinit var adapter: BranchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = BranchAdapter { item ->
            Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
        }

        binding.buttonMap.setOnClickListener {
            Toast.makeText(requireContext(), "Карта скоро будеть", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewBranches.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBranches.adapter = adapter
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getBranches()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.branches.collect { state ->
                    when (state) {
                        is BranchUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is BranchUiState.Success -> {
                            binding.progressBar.isVisible = false
                            adapter.submitList(state.data)
                        }

                        is BranchUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        requireActivity()
            .findViewById<View>(R.id.bottom_navigation)
            .isVisible = false
    }
}