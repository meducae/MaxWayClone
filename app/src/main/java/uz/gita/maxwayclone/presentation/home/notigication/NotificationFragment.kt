package uz.gita.maxwayclone.presentation.home.notigication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentNotificationBinding
import uz.gita.maxwayclone.presentation.adapter.NotificationAdapter
import kotlin.getValue

class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationViewModel by viewModels {
        NotificationFactory()

    }
    private val adapter: NotificationAdapter by lazy { NotificationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationBinding.bind(view)
        binding.arrowLeft.setOnClickListener {
            findNavController().popBackStack()
        }

        setupRecyclerView()
        observeViewModel()
        viewModel.getNotifications()
    }

    private fun setupRecyclerView() {
        binding.recycle.apply {
            adapter = this@NotificationFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.notificationsLiveData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
