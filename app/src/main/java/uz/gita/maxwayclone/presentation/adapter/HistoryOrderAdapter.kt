package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItemHistoryOrderBinding
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryOrderAdapter(private val onItemClick: (UserOrdersUIData) -> Unit): ListAdapter<UserOrdersUIData, HistoryOrderAdapter.VH>(Diff) {
    companion object Diff : DiffUtil.ItemCallback<UserOrdersUIData>(){
        override fun areItemsTheSame(
            oldItem: UserOrdersUIData,
            newItem: UserOrdersUIData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserOrdersUIData,
            newItem: UserOrdersUIData
        ): Boolean {
            return oldItem == newItem
        }

    }
    inner class VH(private val binding: ItemHistoryOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: UserOrdersUIData){
            binding.root.setOnClickListener {
                onItemClick(data)
            }
            binding.numberOrder.text = "№${data.orderNumber}"
            binding.priceOrder.text = String.format("%, d",data.sum)
            val date = Date(data.createTime)
            binding.dateOrder.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
            binding.timeOrder.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return VH(ItemHistoryOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    }