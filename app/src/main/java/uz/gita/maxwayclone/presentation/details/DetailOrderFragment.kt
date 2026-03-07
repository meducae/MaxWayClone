package uz.gita.maxwayclone.presentation.orders.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.data.sources.remote.response.order.my_order.ProductItem
import uz.gita.maxwayclone.databinding.FragmentOrderDetailBinding
import uz.gita.maxwayclone.databinding.ItomOrderDetailBinding
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailOrderFragment : Fragment(R.layout.fragment_order_detail) {
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderDetailBinding.bind(view)

        val orderData = arguments?.getSerializable("order_data") as? UserOrdersUIData

        orderData?.let { data ->
            setupMainInfo(data)
            setupProductList(data.ls)
            updateStepperUI(data.currentStage)
        }
    }

    private fun setupMainInfo(data: UserOrdersUIData) {
        val date = Date(data.createTime)

        binding.apply {
            tvOrderNumber.text = "Заказ №${data.orderNumber}"
            tvTotalAmount.text = "${data.sum} сум"
            statusZakaz.text = "Статус заказа №${data.orderNumber}"
            tvDesc.text = data.statusText
            tvPaymentMethod.text = "Наличные"
            tvOrderDate.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }
    }

    private fun setupProductList(products: List<ProductItem>) {
        binding.containerItems.removeAllViews()

        products.forEach { item ->
            val itemBinding = ItomOrderDetailBinding.inflate(layoutInflater, binding.containerItems, false)
            val product = item.productData

            itemBinding.tvItemName.text = "${product.name} ${item.count}x"

            val totalPricePerItem = product.cost * item.count
            itemBinding.tvItemPrice.text = "$totalPricePerItem сум"

            binding.containerItems.addView(itemBinding.root)
        }
    }

    private fun updateStepperUI(stage: Int) {
        val activeColor = ColorStateList.valueOf("#6A329F".toColorInt())
        val inactiveColor = ColorStateList.valueOf("#E0E0E0".toColorInt())
        val activeTint = "#FFFFFF".toColorInt()
        val inactiveTint = "#6A329F".toColorInt()

        binding.apply {
            stage1.imageTintList = ColorStateList.valueOf(activeTint)
            stage1.backgroundTintList = activeColor

            stage2.imageTintList = ColorStateList.valueOf(if (stage >= 2) activeTint else inactiveTint)
            line1.backgroundTintList = if (stage >= 2) activeColor else inactiveColor
            stage2.backgroundTintList = if (stage >= 2) activeColor else inactiveColor

            stage3.imageTintList = ColorStateList.valueOf(if (stage >= 3) activeTint else inactiveTint)
            line2.backgroundTintList = if (stage >= 3) activeColor else inactiveColor
            stage3.backgroundTintList = if (stage >= 3) activeColor else inactiveColor

            stage4.imageTintList = ColorStateList.valueOf(if (stage >= 4) activeTint else inactiveTint)
            line3.backgroundTintList = if (stage >= 4) activeColor else inactiveColor
            stage4.backgroundTintList = if (stage >= 4) activeColor else inactiveColor
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}