package uz.gita.maxwayclone.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.ItomAdsBinding
import uz.gita.maxwayclone.domain.model.home.AdsModel

class AdsAdapter : ListAdapter<AdsModel, AdsAdapter.VH>(DiffCallBack) {


    override fun getItemCount(): Int {
        return if (currentList.isNotEmpty())10000 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItomAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (currentList.isNotEmpty()){
            val item = currentList[position % currentList.size]
            holder.bind(item)        }
    }

    inner class VH(val binding: ItomAdsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdsModel) {

            binding.apply {
                progress.visibility = View.VISIBLE
                Glide.with(imageView.context)
                    .load(item.imageUrl)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable?>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress.visibility = View.GONE
                            return false                        }

                    })
                    .into(imageView)

            }


           }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<AdsModel>() {
        override fun areItemsTheSame(oldItem: AdsModel, newItem: AdsModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AdsModel, newItem: AdsModel) = oldItem == newItem
    }
}