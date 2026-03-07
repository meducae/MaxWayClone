package uz.gita.maxwayclone.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.Job
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.presentation.pages.StoriesPage

class StoriesShowAdapter(fm : FragmentManager , lifecycle: Lifecycle) : FragmentStateAdapter(fm , lifecycle) {
   private var storiesList = mutableListOf<StoriesModel>()

    override fun createFragment(position: Int): Fragment {
        return StoriesPage.getInstance(storiesList[position])
    }

     override fun getItemCount(): Int = storiesList.size


    fun submitList(stories : List<StoriesModel> ){
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = storiesList.size
            override fun getNewListSize() = stories.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                storiesList[oldItemPosition].id == stories[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                storiesList[oldItemPosition] == stories[newItemPosition]
        })
        storiesList.clear()
        storiesList.addAll(stories)
        diffResult.dispatchUpdatesTo(this)
    }
}