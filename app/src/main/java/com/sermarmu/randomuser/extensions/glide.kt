package com.sermarmu.randomuser.extensions


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImageFromUrlWithRadius(
    url: String,
    transition: DrawableTransitionOptions = DrawableTransitionOptions.withCrossFade()
) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .transition(transition)
        .into(this)
}