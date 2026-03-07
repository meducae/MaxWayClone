package uz.gita.maxwayclone.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItemCategoryBinding
import uz.gita.maxwayclone.domain.model.home.CategoryModel


//private val onClick: (CategoryModel) -> Unit
class CategoriesAdapter: ListAdapter<CategoryModel , CategoriesAdapter.VH>(CategoriesDiffUtil){

    object CategoriesDiffUtil : DiffUtil.ItemCallback<CategoryModel>(){
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

    }
    private var selectedId = -1
    private var onClickListener : ((CategoryModel) -> Unit)?=null
    inner class VH(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val currentPos = absoluteAdapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    onClickListener?.invoke(getItem(currentPos))
                }
            }
        }

        fun bind(item : CategoryModel) {
            item.apply {
                binding.tvCategory.text = name
                binding.tvCategory.isSelected = id == selectedId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }


    fun highlight(categoryId: Int) {
        if (selectedId == categoryId) return
        selectedId = categoryId
        Log.d("TTT", "highlight: ${selectedId}")
        notifyDataSetChanged()
    }

    fun setOnClickListener(block : ((CategoryModel) -> Unit)){
        onClickListener = block
    }
}