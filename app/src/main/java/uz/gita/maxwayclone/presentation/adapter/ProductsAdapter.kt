package uz.gita.maxwayclone.presentation.adapter

import android.R.attr.width
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.UiState.Loading.formatPrice
import uz.gita.maxwayclone.databinding.ItemCategoryHeaderBinding
import uz.gita.maxwayclone.databinding.ItemProductBinding
import uz.gita.maxwayclone.databinding.ItemProductHorizontalBinding
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import kotlin.math.abs

class ProductsAdapter(
    private val onClick:(ProductModel , Int) -> Unit,
    private val isHorizontal: Boolean
) : ListAdapter<ProductTypeModel, RecyclerView.ViewHolder>(ProductDiffUtil) {

    companion object {
        const val TYPE_CATEGORY = 0
        const val TYPE_PRODUCT = 1
    }

    private var onAddClick: ((ProductModel) -> Unit)? = null
    private var onMinusClick: ((Int, Int) -> Unit)? = null

    object ProductDiffUtil : DiffUtil.ItemCallback<ProductTypeModel>() {
        override fun areItemsTheSame(oldItem: ProductTypeModel, newItem: ProductTypeModel): Boolean {
            return when {
                oldItem is ProductTypeModel.CategoryHeader && newItem is ProductTypeModel.CategoryHeader ->
                    oldItem.categoryId == newItem.categoryId

                oldItem is ProductTypeModel.ProductItem && newItem is ProductTypeModel.ProductItem ->
                    oldItem.product.id == newItem.product.id

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ProductTypeModel, newItem: ProductTypeModel): Boolean = oldItem == newItem
    }

    inner class CategoryVH(private val binding: ItemCategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductTypeModel.CategoryHeader) {
            binding.categoryHd.text = item.categoryName
        }
    }

    inner class ProductVerticalVH(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAdd.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onAddClick?.invoke(item.product)
            }
            binding.add.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onAddClick?.invoke(item.product)
            }
            binding.remove.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onMinusClick?.invoke(item.product.id, item.count)
            }

            binding.root.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onClick(item.product , item.count)
            }
        }

        fun bind(productModel: ProductModel, currentCount: Int) {
            productModel.apply {
                binding.tvProductName.text = name
                binding.tvProductPrice.text = "${cost.formatPrice()} сум"
                binding.tvProductDesc.text = description
                Glide.with(binding.tvProductImage)
                    .load(image)
                    .into(binding.tvProductImage)
            }
            if (currentCount > 0) {
                binding.btnAdd.visibility = android.view.View.GONE
                binding.addOrRemove.visibility = android.view.View.VISIBLE
                binding.tvItemCount.text = currentCount.toString()
            } else {
                binding.btnAdd.visibility = android.view.View.VISIBLE
                binding.addOrRemove.visibility = android.view.View.GONE
            }
        }
    }

    inner class ProductHorizontalVH(private val binding: ItemProductHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAdd.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onAddClick?.invoke(item.product)
            }
            binding.add.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onAddClick?.invoke(item.product)
            }
            binding.remove.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onMinusClick?.invoke(item.product.id, item.count)
            }

            binding.root.setOnClickListener {
                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
                onClick(item.product , item.count)
            }
        }

        fun bind(productModel: ProductModel, currentCount: Int) {
            productModel.apply {
                binding.tvProductName.text = name
                binding.tvProductPrice.text = "${cost.formatPrice()} сум"
                binding.tvProductDesc.text = description
                Glide.with(binding.tvProductImage)
                    .load(image)
                    .into(binding.tvProductImage)
            }
            if (currentCount > 0) {
                binding.btnAdd.visibility = android.view.View.GONE
                binding.addOrRemove.visibility = android.view.View.VISIBLE
                binding.tvItemCount.text = currentCount.toString()
            } else {
                binding.btnAdd.visibility = android.view.View.VISIBLE
                binding.addOrRemove.visibility = android.view.View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductTypeModel.ProductItem -> TYPE_PRODUCT
            is ProductTypeModel.CategoryHeader -> TYPE_CATEGORY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CATEGORY -> {
                CategoryVH(
                    ItemCategoryHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> {
                if (isHorizontal) {
                    ProductHorizontalVH(
                        ItemProductHorizontalBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        )
                    )
                } else {
                    ProductVerticalVH(
                        ItemProductBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        )
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ProductTypeModel.ProductItem -> {
                if (holder is ProductHorizontalVH) holder.bind(item.product, item.count)
                if (holder is ProductVerticalVH) holder.bind(item.product, item.count)
            }

            is ProductTypeModel.CategoryHeader -> (holder as CategoryVH).bind(item)
        }
    }

    fun setAdListener(block: (ProductModel) -> Unit) {
        onAddClick = block
    }

    fun setOnDecrementClickListener(block: (Int, Int) -> Unit) {
        onMinusClick = block
    }
}

//class ProductsAdapter : ListAdapter<ProductTypeModel, RecyclerView.ViewHolder>(ProductDiffUtil) {
//    companion object {
//        const val TYPE_CATEGORY = 0
//        const val TYPE_PRODUCT = 1
//    }
//
//    private var onAddClick: ((ProductModel) -> Unit)? = null
//    private var onMinusClick: ((Int , Int) -> Unit)? = null
//
//    object ProductDiffUtil : DiffUtil.ItemCallback<ProductTypeModel>() {
//        override fun areItemsTheSame(oldItem: ProductTypeModel, newItem: ProductTypeModel): Boolean {
//            return when {
//                oldItem is ProductTypeModel.CategoryHeader &&
//                        newItem is ProductTypeModel.CategoryHeader ->
//                    oldItem.categoryId == newItem.categoryId
//                oldItem is ProductTypeModel.ProductItem &&
//                        newItem is ProductTypeModel.ProductItem ->
//                    oldItem.product.id == newItem.product.id
//                else -> false
//            }
//        }
//
//        override fun areContentsTheSame(oldItem: ProductTypeModel, newItem: ProductTypeModel): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    inner class CategoryVH(private val binding: ItemCategoryHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: ProductTypeModel.CategoryHeader) {
//            binding.categoryHd.text = item.categoryName
//        }
//    }
//
//    inner class ProductVH(private val binding: ItemProductHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
//        init {
//            binding.btnAdd.setOnClickListener {
//                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
//                onAddClick?.invoke(item.product)
//            }
//            binding.add.setOnClickListener {
//                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
//                onAddClick?.invoke(item.product)
//            }
//            binding.remove.setOnClickListener {
//                val item = getItem(absoluteAdapterPosition) as ProductTypeModel.ProductItem
//                onMinusClick?.invoke(item.product.id , item.count)
//            }
//        }
//        fun bind(productModel: ProductModel, currentCount: Int) {
//            productModel.apply {
//                binding.tvProductName.text = name
//                binding.tvProductPrice.text = "${cost.formatPrice()} сум"
//                binding.tvProductDesc.text = description
//                Glide.with(binding.tvProductImage)
//                    .load(image)
//                    .into(binding.tvProductImage)
//            }
//            if (currentCount > 0) {
//                binding.btnAdd.visibility = android.view.View.GONE
//                binding.addOrRemove.visibility = android.view.View.VISIBLE
//                binding.tvItemCount.text = currentCount.toString()
//            } else {
//                binding.btnAdd.visibility = android.view.View.VISIBLE
//                binding.addOrRemove.visibility = android.view.View.GONE
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (getItem(position)) {
//            is ProductTypeModel.ProductItem -> TYPE_PRODUCT
//            is ProductTypeModel.CategoryHeader -> TYPE_CATEGORY
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        return when (viewType) {
//            TYPE_CATEGORY -> {
//                CategoryVH(ItemCategoryHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            }
//
//            else -> {
//                val binding = ItemProductHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                ProductVH(binding)
//            }
//
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (val item = getItem(position)) {
//            is ProductTypeModel.ProductItem -> (holder as ProductVH).bind(item.product, item.count)
//            is ProductTypeModel.CategoryHeader -> (holder as CategoryVH).bind(item)
//        }
//    }
//
//    fun setAdListener(block : (ProductModel) -> Unit){
//        onAddClick  = block
//    }
//
//    fun setOnDecrementClickListener(block : (Int , Int) -> Unit){
//        onMinusClick =block
//    }
//
//}