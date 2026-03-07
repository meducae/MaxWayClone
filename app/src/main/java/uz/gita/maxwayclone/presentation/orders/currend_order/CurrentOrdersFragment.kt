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
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
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

        binding.rvCurrentOrders.adapter = adapter

        if (viewModel.ordersFlow.value.isEmpty()) {
            showShimmer()
            binding.emptyState.isVisible = false
            binding.rvCurrentOrders.isVisible = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loaderFlow.onEach { isLoading ->
            if (isLoading) {
                showShimmer()
                binding.emptyState.isVisible = false
            } else {
                hideShimmer()
                checkEmptyState(viewModel.ordersFlow.value)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ordersFlow.collectLatest { orders ->
                adapter.submitList(orders)

                if (!viewModel.loaderFlow.value) {
                    checkEmptyState(orders)
                }
            }
        }
    }

    private fun checkEmptyState(orders: List<UserOrdersUIData>) {
        if (orders.isEmpty()) {
            binding.emptyState.isVisible = true
            binding.rvCurrentOrders.isVisible = false
        } else {
            binding.emptyState.isVisible = false
            binding.rvCurrentOrders.isVisible = true
        }
    }    private fun showShimmer() {
        binding.shimmerCurrent.apply {
            isVisible = true
            startShimmer()
        }
        binding.rvCurrentOrders.isVisible = false
    }

    private fun hideShimmer() {
        binding.shimmerCurrent.apply {
            stopShimmer()
            isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}