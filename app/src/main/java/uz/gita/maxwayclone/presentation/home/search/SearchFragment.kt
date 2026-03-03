package uz.gita.maxwayclone.presentation.home.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentSearchBinding
import uz.gita.maxwayclone.presentation.adapter.SearchAdapter

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels {
        SearchFactory()
    }

    private val adapter: SearchAdapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        setupUI()
        observeViewModel()

    }

    private fun setupUI() {
        binding.rvSearch.adapter = adapter

        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.search(query)
            } else {
                adapter.submitList(emptyList())
                binding.layoutEmptyState.visibility = View.VISIBLE
            }
        }

        adapter.setOnItemClickListener { product ->
            val bundle = Bundle().apply {
                putSerializable("product", product)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_productDetailsFragment,
                bundle
            )
        }

        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

            if (list.isNullOrEmpty() && binding.etSearch.text.isNotEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvSearch.visibility = View.VISIBLE
            }
        }

        viewModel.loaderLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}