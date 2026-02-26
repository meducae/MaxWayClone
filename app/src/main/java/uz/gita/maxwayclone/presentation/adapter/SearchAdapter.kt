package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.databinding.ItomSearchProductBinding
import uz.gita.maxwayclone.domain.model.home.SearchModel

class SearchAdapter : ListAdapter<SearchModel, SearchAdapter.VH>(DiffCallBack) {
    private var onItemClickListener: ((SearchModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (SearchModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return VH(ItomSearchProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class VH(val binding: ItomSearchProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model :SearchModel){
            binding.apply {
                tvProductName.text = model.name
                tvProductDesc.text = model.description
                tvProductPrice.text = model.cost.toString()
                Glide.with(imgProduct.context)
                    .load(model.image)
                    .into(imgProduct)
                root.setOnClickListener {
                    onItemClickListener?.invoke(model)
                }
            }
        }
    }
    companion object DiffCallBack: DiffUtil.ItemCallback<SearchModel>(){
        override fun areItemsTheSame(
            oldItem: SearchModel,
            newItem: SearchModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchModel,
            newItem: SearchModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}