package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                Glide.with(imgNotification.context)
                    .load(model.imgUrl)
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