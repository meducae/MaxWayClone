package uz.gita.maxwayclone.presentation.home.stories

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.MainStoriesBinding
import uz.gita.maxwayclone.presentation.adapter.StoriesShowAdapter

class StoriesFragment : Fragment(R.layout.main_stories) {
    private lateinit var adapter: StoriesShowAdapter
    private var job: Job? = null
    private val viewModel: StoriesViewModel by viewModels { StoriesViewModelFactory() }
    private var progress = 0
    private var isPaused = false
    private val duration = 5000L
    private val interval = 50L
    private val steps = (duration / interval).toInt()
    private var currentPositon = 0

    private val binding by viewBinding(MainStoriesBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StoriesShowAdapter(childFragmentManager, lifecycle)
        binding.viewPagerStories.adapter = adapter
        currentPositon = arguments?.getInt("position")?:0
        viewModel.loadStories()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showStories.collect { data ->
                    adapter.submitList(data)
                    viewLifecycleOwner.lifecycleScope.launch {
                        binding.viewPagerStories.setCurrentItem(currentPositon , false)
                    }
                }
            }
        }

        startProgress()
        binding.viewPagerStories.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                job?.cancel()
                binding.storyProgressBar.progress = 0
                startProgress()
            }
        })
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }


        val internalViewPagerChild = binding.viewPagerStories.getChildAt(0)
            internalViewPagerChild.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    isPaused = true
                }

                MotionEvent.ACTION_UP , MotionEvent.ACTION_CANCEL ->{
                    isPaused = false
                }
            }
            false
        }
    }


    private fun startProgress() {
        job?.cancel()
        progress = 0
        binding.storyProgressBar.progress = progress

        job = viewLifecycleOwner.lifecycleScope.launch {
            while (progress < 100) {
                if (!isPaused) {
                    progress += 100 / steps
                }
                binding.storyProgressBar.progress = progress.coerceAtMost(100)
                delay(interval)
            }
            moveToNextStory()
        }
    }

    private fun moveToNextStory() {
        val next = (binding.viewPagerStories.currentItem + 1) % adapter.itemCount
        binding.viewPagerStories.currentItem = next
    }


    override fun onResume() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        super.onResume()
    }

    override fun onStop() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        super.onStop()
    }

    override fun onDestroyView() {
        job?.cancel()
        job = null
        super.onDestroyView()
    }
}