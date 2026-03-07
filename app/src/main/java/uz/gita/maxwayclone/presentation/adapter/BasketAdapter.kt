package uz.gita.maxwayclone.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.databinding.ItemProductMenuBinding
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.ProductModel

class BasketAdapter : ListAdapter<BasketModel, BasketAdapter.VH>(BasketsDiffUtil) {

    private var onAddClick: ((productId : Int) -> Unit)? = null
    private var onMinusClick: ((Int , Int) -> Unit)? = null

    object BasketsDiffUtil : DiffUtil.ItemCallback<BasketModel>() {
        override fun areItemsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
            return oldItem == newItem
        }

    }

    inner class VH(private val binding: ItemProductMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnPlus.setOnClickListener {
                onAddClick?.invoke(getItem(absoluteAdapterPosition).productId)
            }
            binding.btnMinus.setOnClickListener {
                val item = getItem(absoluteAdapterPosition)
                onMinusClick?.invoke(item.productId , item.count)
            }
        }
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.tvTitle.text = name
                binding.tvDescription.text = description
                binding.tvCount.text = count.toString()
                binding.tvPrice.text = " ${cost.toString()} sum"
                Glide.with(binding.imgProduct).load(imageUrl).into(binding.imgProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemProductMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    fun setAddListener(block : (productId : Int) -> Unit){
        onAddClick = block
    }
    fun setDecrementListener(block : (Int , Int) -> Unit) {
        onMinusClick = block
    }

}