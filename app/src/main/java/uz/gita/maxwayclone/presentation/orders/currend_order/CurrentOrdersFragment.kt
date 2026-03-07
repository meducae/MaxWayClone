package uz.gita.maxwayclone.presentation.orders.currend_order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentCurrendOrderBinding
import uz.gita.maxwayclone.presentation.adapter.CurrentOrdersAdapter

class CurrentOrdersFragment : Fragment(R.layout.fragment_currend_order) {
    private var _binding: FragmentCurrendOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrentOrdersViewModel by viewModels { CurrentOrdersViewModelFactory() }
    private val adapter = CurrentOrdersAdapter { order ->
        val bundle = Bundle().apply {
            putSerializable("order_data", order)
        }
        findNavController().navigate(R.id.orderDetailFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrendOrderBinding.bind(view)
        if (viewModel.ordersFlow.value.isEmpty()) {
            binding.progressBar.isVisible = true
            binding.emptyState.isVisible = false
            binding.rvCurrentOrders.isVisible = false
        }

        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        binding.rvCurrentOrders.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.loaderFlow.onEach { isLoading ->
            binding.progressBar.isVisible = isLoading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ordersFlow.collectLatest { orders ->

                binding.progressBar.isVisible = false

                if (orders.isEmpty()) {
                    binding.emptyState.isVisible = true
                    binding.rvCurrentOrders.isVisible = false
                } else {
                    binding.emptyState.isVisible = false
                    binding.rvCurrentOrders.isVisible = true
                    adapter.submitList(orders)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorFlow.collect { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderSuccessFlow.collect {
                Toast.makeText(requireContext(), "Tayyorlandi! Buyurtma tarixga o'tkazildi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}