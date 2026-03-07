package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItemHistoryOrderBinding
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryOrderAdapter: ListAdapter<MyOrdersUIData, HistoryOrderAdapter.VH>(Diff) {
    companion object Diff : DiffUtil.ItemCallback<MyOrdersUIData>(){
        override fun areItemsTheSame(
            oldItem: MyOrdersUIData,
            newItem: MyOrdersUIData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MyOrdersUIData,
            newItem: MyOrdersUIData
        ): Boolean {
            return oldItem == newItem
        }

    }
    inner class VH(private val binding: ItemHistoryOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: MyOrdersUIData){
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