package uz.gita.maxwayclone.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentHomeBinding
import uz.gita.maxwayclone.presentation.adapter.AdsAdapter
import uz.gita.maxwayclone.presentation.adapter.StoriesItemAdapter
import kotlin.getValue

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory() }
    private lateinit var adapter: AdsAdapter
    private val adapterStories by lazy { StoriesItemAdapter() }

    private val handler = Handler(Looper.getMainLooper())

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val viewPager = _binding?.viewPagerCarousel ?: return
            if (adapter.itemCount == 0) return

            val nextItem = viewPager.currentItem + 1
            viewPager.setCurrentItem(nextItem, true)

            handler.removeCallbacks(this)
            handler.postDelayed(this, 4000)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupAdapter()
        observeViewModel()
        viewModel.fetchAds()
        setStoriesItem()
        viewModel.loadStories()
        Log.d("TTT", "HomeFragment: Keldi")
    }




    private fun setStoriesItem() {
        binding.storiesCircle.adapter = adapterStories
        binding.storiesCircle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.storiesFlowData.collect { data ->
                    Log.d("TTT", "setStoriesItem: $data")
                    adapterStories.submitList(data)
                }
            }
        }
        adapterStories.setListener { position ->
            val bundle = Bundle().apply {
                putInt("position", position)
            }
            findNavController().navigate(R.id.action_nav_home_to_storiesFragment , bundle)
        }
    }


    private fun setupAdapter() {
        adapter = AdsAdapter()
        binding.viewPagerCarousel.adapter = adapter

        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    handler.removeCallbacks(autoScrollRunnable)
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    resetAutoScroll()
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

                binding.viewPagerCarousel.setCurrentItem(startPosition, false)

                resetAutoScroll()
            }
        }
    }


    private fun resetAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
        handler.postDelayed(autoScrollRunnable, 4000)
    }

    override fun onPause() {
        handler.removeCallbacks(autoScrollRunnable)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        resetAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
        _binding = null
    }


}