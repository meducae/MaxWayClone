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
import uz.gita.maxwayclone.databinding.ItomNotificationBinding
import uz.gita.maxwayclone.domain.model.home.NotificationModel

class NotificationAdapter: ListAdapter<NotificationModel, NotificationAdapter.VH>(Diff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItomNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class VH(val binding: ItomNotificationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: NotificationModel){
            binding.apply {
               txtName.text = model.name
                txtDescription.text = model.massage
                txtDate.text = model.date
                progress.visibility = View.VISIBLE

                Glide.with(imgNotification.context)
                    .load(model.imgUrl)
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
                            return false
                        }

                    })
                    .into(imgNotification)

            }


        }
    }
    companion object Diff : DiffUtil.ItemCallback<NotificationModel>(){
        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}