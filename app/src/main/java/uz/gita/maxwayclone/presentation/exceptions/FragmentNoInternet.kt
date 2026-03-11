package uz.gita.maxwayclone.presentation.exceptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayclone.InternetMonitor
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentNoConnectionBinding


class FragmentNoInternet : Fragment(R.layout.fragment_no_connection) {

    private lateinit var internetMonitor: InternetMonitor
    private val binding by viewBinding(FragmentNoConnectionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        internetMonitor = InternetMonitor(requireContext())
        binding.buttonAgain.setOnClickListener {
            internetMonitor.registerListener { isConnected ->
                if (isConnected) {
                    findNavController().navigate(R.id.action_fragmentNoInternet_to_nav_home)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        internetMonitor.stopMonitoring()
    }

}