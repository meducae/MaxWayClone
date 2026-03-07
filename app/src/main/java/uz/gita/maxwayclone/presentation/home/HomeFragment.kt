package uz.gita.maxwayclone.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.InternetMonitor
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentHomeBinding
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.presentation.adapter.AdsAdapter
import uz.gita.maxwayclone.presentation.adapter.CategoriesAdapter
import uz.gita.maxwayclone.presentation.adapter.ProductsAdapter
import uz.gita.maxwayclone.presentation.adapter.StoriesItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory()
    }
    private lateinit var adapter: AdsAdapter
    private lateinit var storiesAdapter: StoriesItemAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var internetMonitor: InternetMonitor
    private lateinit var layoutManager: GridLayoutManager
    private var oldStatusBarColor = 0
    private var autoScrollJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        internetMonitor = InternetMonitor(requireContext())
        internetMonitor.registerListener { isConnected ->
            if (!isConnected) {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_nav_home_to_fragmentNoInternet)
                }
            }
        }
        internetMonitor.startMonitoring()
        binding.shimmerLoading.startShimmer()
        binding.homeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_searchFragment)
        }


        adapter = AdsAdapter()
        storiesAdapter = StoriesItemAdapter()
        categoriesAdapter = CategoriesAdapter()
        productsAdapter = ProductsAdapter(
            { product, count ->
                val bundle = Bundle().apply {
                    putSerializable("product", product)
                    putInt("count", count)
                }
                findNavController().navigate(R.id.productDetailsFragment, bundle)
            },
            false
        )
        binding.notification.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_notificationFragment)
        }
        setupAdapter()
        observeViewModel()
        setupStoriesAdapter()
        categoriesConfiguration()
        storiesAdapter.setListener { position ->
            val bundle = Bundle().apply {
                putInt("position", position)
            }
            findNavController().navigate(R.id.action_nav_home_to_storiesFragment, bundle)
        }




        binding.products.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstVisible == RecyclerView.NO_POSITION) return

                if (firstVisible >= 0 && firstVisible < productsAdapter.currentList.size) {
                    val product = productsAdapter.currentList[firstVisible]
                    val categoryId = when (product) {
                        is ProductTypeModel.ProductItem -> product.product.categoryID
                        is ProductTypeModel.CategoryHeader -> product.categoryId
                    }
                    categoriesAdapter.highlight(categoryId)
                    val categoryIndex = categoriesAdapter.currentList.indexOfFirst { it.id == categoryId }
                    if (categoryIndex != -1) {
                        binding.categories.smoothScrollToPosition(categoryIndex)
                    }
                }
            }
        })



        productsAdapter.setAdListener { productModel ->
            viewModel.addBasket(productModel)
        }
        productsAdapter.setOnDecrementClickListener { id, currentCount ->
            viewModel.removeBasket(id, currentCount)
        }
//        productsAdapter.
    }

    private fun categoriesConfiguration() {
        categoriesAdapter.setOnClickListener { categoryModel ->
            val index = productsAdapter.currentList.indexOfFirst { item ->
                when (item) {
                    is ProductTypeModel.CategoryHeader -> item.categoryId == categoryModel.id
                    is ProductTypeModel.ProductItem -> item.product.categoryID == categoryModel.id
                }
            }
            if (index != -1) {
                if (index == 0) {
                    binding.appBarLayout.setExpanded(true, true)
                } else {
                    binding.appBarLayout.setExpanded(false, false)
                }
                binding.products.post {
                    layoutManager.scrollToPositionWithOffset(index, 0)
                }
            }
            categoriesAdapter.highlight(categoryModel.id)
        }
    }

    private fun setupStoriesAdapter() {
        binding.storiesCircle.adapter = storiesAdapter
        binding.storiesCircle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    private fun setupAdapter() {
        binding.viewPagerCarousel.adapter = adapter
        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    stopAutoScroll()
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    startAutoScroll()
                }
            }
        })
        layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productsAdapter.getItemViewType(position)) {
                    ProductsAdapter.TYPE_CATEGORY -> 2
                    ProductsAdapter.TYPE_PRODUCT -> 1
                    else -> 1
                }
            }

        }
        binding.categories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.products.layoutManager = layoutManager
        binding.products.adapter = productsAdapter
        (binding.products.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.categories.adapter = categoriesAdapter
    }


    private fun observeViewModel() {
        viewModel.adsLiveData.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) return@observe

            adapter.submitList(list) {
                val middle = 10000 / 2
                val startPosition = middle - (middle % list.size)
                if (binding.viewPagerCarousel.currentItem == 0) {
                    binding.viewPagerCarousel.setCurrentItem(startPosition, false)
                }
                startAutoScroll()
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.storiesFlowData.collectLatest { data ->
                storiesAdapter.submitList(data)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsFlowData.collect { productsData ->
                Log.d("TTT", "observeViewModel: ${productsData.size}")
                if (productsData.isNotEmpty()) {
                    Log.d("TTT", "observeViewModelnotEmpty: ${productsData.size}")
                    productsAdapter.submitList(productsData)
                    delay(500)
                    binding.shimmerLoading.stopShimmer()
                    binding.shimmerLoading.visibility = View.GONE
                    binding.menuHome.visibility = View.VISIBLE
                }
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.fragment404Exception)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoriesFlowData.collectLatest { categoryModels ->
                categoriesAdapter.submitList(categoryModels)
            }
        }
    }


    private fun startAutoScroll() {
        stopAutoScroll()
        autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(4000)
                val nextItem = binding.viewPagerCarousel.currentItem + 1
                binding.viewPagerCarousel.setCurrentItem(nextItem, true)
            }
        }
    }

    private fun stopAutoScroll() {
        autoScrollJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        oldStatusBarColor = requireActivity().window.statusBarColor
        requireActivity().window.statusBarColor = Color.WHITE
    }

    override fun onPause() {
        stopAutoScroll()
        super.onPause()
        requireActivity().window.statusBarColor = oldStatusBarColor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPagerCarousel.adapter = null
        binding.storiesCircle.adapter = null
        binding.categories.adapter = null
        binding.products.adapter = null
        internetMonitor.stopMonitoring()
        _binding = null
    }
}