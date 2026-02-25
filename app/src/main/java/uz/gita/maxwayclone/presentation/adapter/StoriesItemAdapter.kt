package uz.gita.maxwayclone.presentation.adapter

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.databinding.ItemStoriesBinding
import uz.gita.maxwayclone.domain.model.home.StoriesModel

class StoriesItemAdapter : ListAdapter<StoriesModel,StoriesItemAdapter.StoriesVH>(DiffCallBack) {
    companion object DiffCallBack : DiffUtil.ItemCallback<StoriesModel>() {
        override fun areItemsTheSame(oldItem: StoriesModel, newItem: StoriesModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: StoriesModel, newItem: StoriesModel) = oldItem == newItem
    }

    private var listener : ((position : Int) -> Unit)?=null
    inner class StoriesVH(val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener?.invoke(absoluteAdapterPosition)
            }
        }
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.name.text =name
                Glide.with(binding.imageStories)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.progress_indeterminate_horizontal)
                    .into(binding.imageStories)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesVH = StoriesVH(ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent , false))

    override fun onBindViewHolder(holder: StoriesVH, position: Int) = holder.bind()

    fun setListener(block : ((position : Int) -> Unit)){
        listener = block
    }


}