package uz.gita.maxwayclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItemBranchesBinding
import uz.gita.maxwayclone.domain.model.branch.Branch

class BranchAdapter(
    private val onClick: (Branch) -> Unit
) : ListAdapter<Branch, BranchAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Branch>() {
        override fun areItemsTheSame(oldItem: Branch, newItem: Branch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Branch, newItem: Branch): Boolean {
            return oldItem == newItem
        }
    }

    inner class VH(private val binding: ItemBranchesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Branch){
            binding.nameBranch.text = item.name
            binding.addressBranch.text = item.address
            binding.phoneBranch.text = item.phone
            binding.workTime.text = "${item.openTime}-${item.closeTime}"
            binding.deliveryTime.text = "${item.openTime}-${item.closeTime}"

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemBranchesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}