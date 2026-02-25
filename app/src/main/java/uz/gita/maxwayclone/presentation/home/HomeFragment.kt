package uz.gita.maxwayclone.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentHomeBinding
import uz.gita.maxwayclone.presentation.adapter.AdsAdapter
import uz.gita.maxwayclone.presentation.adapter.StoriesItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory()
    }
    private  val adapter: AdsAdapter  by lazy { AdsAdapter() }
    private val storiesAdapter : StoriesItemAdapter by lazy { StoriesItemAdapter() }
    private var autoScrollJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupAdapter()
        observeViewModel()
        setupStoriesAdapter()
        storiesAdapter.setListener { position ->
            val bundle = Bundle().apply {
                putInt("position" , position)
            }
            findNavController().navigate(R.id.action_nav_home_to_storiesFragment , bundle)
        }
    }

    private fun setupStoriesAdapter(){
        binding.storiesCircle.adapter = storiesAdapter
        binding.storiesCircle.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
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
    }



    private fun observeViewModel() {
        viewModel.adsLiveData.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) return@observe
            adapter.submitList(list) {
                val middle = 10000 / 2
                val startPosition = middle - (middle % list.size)

                if (binding.viewPagerCarousel.currentItem == 0) {
                    binding.viewPagerCarousel.setCurrentItem(startPosition, true)
                }
                startAutoScroll()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.storiesFlowData.collect { data ->
                    storiesAdapter.submitList(data)
                }
            }
        }
    }
    private fun startAutoScroll() {
        stopAutoScroll()


        autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(4000) // Handler.postDelayed o'rniga
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

        if (adapter.itemCount > 0) startAutoScroll()
    }

    override fun onPause() {
        stopAutoScroll()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPagerCarousel.adapter = null
        binding.storiesCircle.adapter = null
        _binding = null
    }
}