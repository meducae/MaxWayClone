package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.ItomSearchProductBinding
import uz.gita.maxwayclone.domain.model.home.SearchModel
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import uz.gita.maxwayclone.UiState.Loading.formatPrice

class SearchAdapter : ListAdapter<SearchModel, SearchAdapter.VH>(DiffCallBack) {
    private var onItemClickListener: ((SearchModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (SearchModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItomSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItomSearchProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SearchModel) {
            binding.apply {

                tvProductName.text = model.name
                tvProductDesc.text = model.description
                tvProductPrice.text = "${model.cost.formatPrice()} сум"

                itemProgress.visibility = View.VISIBLE

                Glide.with(imgProduct.context)
                    .load(model.image)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemProgress.visibility = View.GONE
                            return false                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable?>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemProgress.visibility = View.GONE
                            return false                        }


                    })
                    .into(imgProduct)

                root.setOnClickListener {
                    onItemClickListener?.invoke(model)
                }
            }
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel) = oldItem == newItem
    }
}