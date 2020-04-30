package com.drdlee.mviexercise_livedata.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImage")
fun bindingImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView)
            .load(imageUri)
            .into(imageView)
    }
}

@BindingAdapter("setText")
fun bindingText(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}