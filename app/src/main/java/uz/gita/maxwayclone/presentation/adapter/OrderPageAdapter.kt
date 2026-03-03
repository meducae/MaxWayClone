package uz.gita.maxwayclone.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayclone.presentation.orders.currend_order.CurrentOrdersFragment
import uz.gita.maxwayclone.presentation.orders.history_order.HistoryOrdersFragment

class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryOrdersFragment()
            else -> CurrentOrdersFragment()
        }
    }
}