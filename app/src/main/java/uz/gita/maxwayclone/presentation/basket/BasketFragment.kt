package uz.gita.maxwayclone.presentation.basket

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.FragmentBasketBinding
import uz.gita.maxwayclone.presentation.adapter.BasketAdapter
import uz.gita.maxwayclone.presentation.adapter.ProductsAdapter

class BasketFragment : BottomSheetDialogFragment(R.layout.fragment_basket) {
    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val viewModel: BasketViewModel by viewModels { BasketViewModelFactory() }
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var recommendedAdapter: ProductsAdapter
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                // Bu qator Ekranni to'liq egallashga (orqaga kirib ketishga) ruxsat beradi
                sheet.fitsSystemWindows = false

                // BU YERDA BIZ QOIDANI BELGILAYMIZ: "Tepadan StatusBar, pastdan NavigationBar qancha bo'lsa, o'shancha qisib qo'y (Padding ber)"
                ViewCompat.setOnApplyWindowInsetsListener(sheet) { view, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    // Yuqoridan va Pastdan telefonning o'z qoraygan joyi qadar padding beramiz
                    view.setPadding(0, systemBars.top, 0, systemBars.bottom)
                    insets
                }
            }
        }
        return dialog
    }


    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let { sheet ->
            val screenHeight = resources.displayMetrics.heightPixels
            val halfRatio = 0.65f
            val peekPx = (screenHeight * halfRatio).toInt()

            // Sheet har doim to'liq ekran balandligida turadi — hech qachon o'zgarmaydi
            sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            sheet.requestLayout()

            val behavior = BottomSheetBehavior.from(sheet)
            behavior.isFitToContents = false
            behavior.halfExpandedRatio = halfRatio
            behavior.peekHeight = peekPx
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior.isHideable = true
            behavior.skipCollapsed = true

            // Boshida payContainer ni darhol to'g'ri joyga qo'yamiz (lag yo'q!)
            val initialTranslation = -(screenHeight * (1f - halfRatio))
            binding.payContainer.translationY = initialTranslation

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset >= 0f) {
                        val translation = -(screenHeight * (1f - halfRatio)) * (1f - slideOffset)
                        binding.payContainer.translationY = translation
                    }
                }
            })
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basketAdapter = BasketAdapter()
        recommendedAdapter = ProductsAdapter()
        setAdapter()
        loadObserveData()
        basketAdapter.setAddListener { productId ->
            viewModel.plusBasket(productId)
        }
        basketAdapter.setDecrementListener { id, count ->
            viewModel.removeBasket(id, count)
        }

        recommendedAdapter.setAdListener { product ->
            viewModel.addBasket(product)
        }
        recommendedAdapter.setOnDecrementClickListener { id, count ->
            viewModel.removeBasket(id, count)
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
        binding.delete.setOnClickListener {
            viewModel.clearBasket()
        }
    }


    fun setAdapter() {
        binding.basketProducts.adapter = basketAdapter
        binding.basketProducts.layoutManager = LinearLayoutManager(requireContext())
        (binding.basketProducts.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recommendation.adapter = recommendedAdapter
        binding.recommendation.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        (binding.recommendation.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
    }

    fun loadObserveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showBasketItems.collectLatest { basketModels ->
                basketAdapter.submitList(basketModels)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showPrice.collectLatest { price ->
                binding.totalPrice.text = "$price сум"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recommendedFlow.collectLatest { items ->
                recommendedAdapter.submitList(items)
            }
        }
    }
}

