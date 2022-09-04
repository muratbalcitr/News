package com.m2lifeapps.challenge.core.bindings

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.makeramen.roundedimageview.RoundedImageView

@BindingAdapter("app:imageUrl")
fun AppCompatImageView.loadImage(uri:String){
    this.load(uri)
}
@BindingAdapter("app:imageUrl")
fun RoundedImageView.loadImage(uri:String){
    this.load(uri)
}