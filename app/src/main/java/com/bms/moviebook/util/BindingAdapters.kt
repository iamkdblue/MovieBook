package com.bms.moviebook.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


class BindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("poster_url")
        fun ImageView.loadPosterImage(
            profileUrl: String?
        ) {
            GlideLoader(context).loadImage(
                profileUrl,
                this
            )
        }

        @JvmStatic
        @BindingAdapter("cast_url")
        fun ImageView.loadCastImage(
            profileUrl: String?
        ) {
            GlideLoader(context).loadWithCircleCrop(
                profileUrl,
                this
            )
        }

        @JvmStatic
        @BindingAdapter("image_url")
        fun ImageView.loadImage(profileUrl: String?)
        {
            GlideLoader(context).loadImage(profileUrl, this)

        }

    }
}