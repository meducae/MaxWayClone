package uz.gita.maxwayclone.domain.model.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoriesModel(
    val id : Int,
    val name : String,
    val imageUrl : String
) : Parcelable