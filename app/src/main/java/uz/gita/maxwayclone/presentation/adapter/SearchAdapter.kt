package uz.gita.maxwayclone.presentation.adapter

import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayclone.databinding.ItomSearchProductBinding
import uz.gita.maxwayclone.domain.model.home.SearchModel

class SearchAdapter : ListAdapter<SearchModel, SearchAdapter.VH>(DiffCallBack){

    inner class VH(val binding: ItomSearchProductBinding): RecyclerView.ViewHolder(binding.root){

    }
}