package uz.gita.maxwayclone.presentation.orders.history_order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentHistoryBinding
import uz.gita.maxwayclone.presentation.adapter.HistoryOrderAdapter

class HistoryOrdersFragment : Fragment(R.layout.fragment_history){
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryOrderViewModel by viewModels { HistoryOrderFactory() }
    private val adapter = HistoryOrderAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)

        binding.recycle.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.historyOrdersFlow.collectLatest {
                adapter.submitList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingFlow.collectLatest { isLoading->
                if (isLoading){
                    binding.shimmerHistory.startShimmer()
                    binding.shimmerHistory.visibility = View.VISIBLE
                    binding.recycle.visibility = View.GONE
                }else{
                    binding.shimmerHistory.stopShimmer()
                    binding.shimmerHistory.visibility = View.GONE
                    binding.recycle.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}