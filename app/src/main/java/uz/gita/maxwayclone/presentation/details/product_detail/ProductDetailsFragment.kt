package uz.gita.maxwayclone.presentation.details.product_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentProductDetailsBinding
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.SearchModel

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)

        val product = arguments?.getSerializable("product") as? SearchModel
        val product1 = arguments?.getSerializable("product") as? ProductModel
        val count = arguments?.getInt("count")

        product?.let { item ->
            binding.apply {
                Glide.with(requireContext())
                    .load(item.image)
                    .into(imgDetail)
                tvDetailTitle.text = item.name
                tvDetailDesc.text = item.description
                cost.text = "${item.cost} сум"

                name.text = item.name
            }
        }

       product1?.let { item ->
            binding.apply {
                Glide.with(requireContext())
                    .load(item.image)
                    .into(imgDetail)
                tvDetailTitle.text = item.name
                tvDetailDesc.text = item.description
                cost.text = "${item.cost} сум"
                binding.tvCount.text = count.toString()
                name.text = item.name
            }
        }

        binding.arrowLeft.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}