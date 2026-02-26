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

        // Qidiruv maydoniga matn yozilganda
        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.search(query)
            } else {
                adapter.submitList(emptyList())
                binding.layoutEmptyState.visibility = View.VISIBLE
            }
        }

        // Mahsulot bosilganda Details sahifasiga o'tish
        adapter.setOnItemClickListener { product ->
            // Navigation orqali o'tish (SafeArgs bilan product jo'natish mumkin)
            // findNavController().navigate(SearchFragmentDirections.actionToDetails(product))
        }

        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

            // Natija yo'qligini tekshirish
            if (list.isNullOrEmpty() && binding.etSearch.text.isNotEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvSearch.visibility = View.VISIBLE
            }
        }

        viewModel.loaderLiveData.observe(viewLifecycleOwner) { isLoading ->
            // Progress bar ko'rsatish mantiqi
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}