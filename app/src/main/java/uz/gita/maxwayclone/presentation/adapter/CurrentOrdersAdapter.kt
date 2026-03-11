package uz.gita.maxwayclone.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItemMyOrderBinding
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData

class CurrentOrdersAdapter (private val onItemClick: (UserOrdersUIData) -> Unit): ListAdapter<UserOrdersUIData, CurrentOrdersAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(private val binding: ItemMyOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserOrdersUIData) {
            binding.apply {
                statusZakaz.text = "Статус заказа №${data.orderNumber}"
                root.setOnClickListener {
                    onItemClick(data)
                }
                tvDesc.text = data.statusText

                updateStepper(data.currentStage)
            }
        }

        private fun updateStepper(stage: Int) {
            val activeColor = ColorStateList.valueOf("#6A329F".toColorInt())
            val inactiveColor = ColorStateList.valueOf("#E0E0E0".toColorInt())
            val activeTint = "#FFFFFF".toColorInt()
            val inactiveTint = "#6A329F".toColorInt()

            binding.apply {

                stage1.imageTintList = ColorStateList.valueOf(activeTint)
                stage1.backgroundTintList = activeColor

                stage2.imageTintList = ColorStateList.valueOf(if (stage >= 2) activeTint else inactiveTint)
                line1.backgroundTintList = if (stage >= 2) activeColor else inactiveColor
                stage2.backgroundTintList = if (stage >= 2) activeColor else inactiveColor

                stage3.imageTintList = ColorStateList.valueOf(if (stage >= 3) activeTint else inactiveTint)
                line2.backgroundTintList = if (stage >= 3) activeColor else inactiveColor
                stage3.backgroundTintList = if (stage >= 3) activeColor else inactiveColor

                stage4.imageTintList = ColorStateList.valueOf(if (stage >= 4) activeTint else inactiveTint)

                line3.backgroundTintList = if (stage >= 4) activeColor else inactiveColor
                stage4.backgroundTintList = if (stage >= 4) activeColor else inactiveColor
            }
        }    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<UserOrdersUIData>() {
        override fun areItemsTheSame(oldItem: UserOrdersUIData, newItem: UserOrdersUIData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserOrdersUIData, newItem: UserOrdersUIData): Boolean {
            return oldItem == newItem
        }
    }
}