package com.codelabs.southsystem.eventos.core.helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.codelabs.southsystem.eventos.MyApplication
import com.codelabs.southsystem.eventos.R

object GlideHelper {

    fun load(url: String?, imageView: ImageView) {
        Glide
            .with(MyApplication.instance.applicationContext)
            .load(url)
            .transition(getFadeTransition())
            .error(R.drawable.error_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun getFadeTransition() = DrawableTransitionOptions.withCrossFade(
        DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    )
}