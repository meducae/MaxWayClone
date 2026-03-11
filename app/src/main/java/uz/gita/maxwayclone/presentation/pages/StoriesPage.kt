package uz.gita.maxwayclone.presentation.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.PageStoriesBinding
import uz.gita.maxwayclone.domain.model.home.StoriesModel

class StoriesPage private constructor(): Fragment(R.layout.page_stories) {
    private lateinit var binding : PageStoriesBinding
    private var story : StoriesModel?=null
    companion object{
        fun getInstance(story : StoriesModel) : StoriesPage{
            val fragment = StoriesPage()
            val bundle = Bundle()
             bundle.putParcelable("data" , story)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        story = arguments?.getParcelable("data")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageStoriesBinding.bind(view)
        Glide.with(binding.profileAvatar)
            .load(story?.imageUrl)
            .into(binding.profileAvatar)
        Glide.with(binding.storiesImage)
            .load(story?.imageUrl)
            .into(binding.storiesImage)
        binding.nameProfile.text = story?.name
    }





}