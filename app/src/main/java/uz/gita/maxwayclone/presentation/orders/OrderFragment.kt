package uz.gita.maxwayclone.presentation.orders

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.InternetMonitor
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentHomeBinding
import uz.gita.maxwayclone.databinding.FragmentOrderBinding
import uz.gita.maxwayclone.presentation.adapter.OrdersPagerAdapter

class OrderFragment: Fragment(R.layout.fragment_order) {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var internetMonitor: InternetMonitor
    private lateinit var adapter : OrdersPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderBinding.bind(view)
        internetMonitor = InternetMonitor(requireContext())
        internetMonitor.registerListener { isConnected ->
            if (!isConnected) {
                lifecycleScope.launch (Dispatchers.Main){
                    findNavController().navigate(R.id.action_nav_orders_to_fragmentNoInternet)
                }
            }
        }
        internetMonitor.startMonitoring()
        adapter = OrdersPagerAdapter(this)

        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "История заказов"
                else -> "Текущие заказы"
            }
        }.attach()
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        binding.tabLayout.setTabRippleColor(ColorStateList.valueOf(Color.TRANSPARENT))


        binding.viewPager.setCurrentItem(1, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        internetMonitor.stopMonitoring()
    }
}