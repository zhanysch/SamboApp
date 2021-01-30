package com.example.sambo.utils.ext

import android.content.Context
import android.util.TypedValue
import android.view.View
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

fun Context.dpToPx(dimens: Float?): Int {
    val metrics = applicationContext.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimens ?: 0f, metrics)
        .toInt()
}

fun ShapeableImageView.setCornerRadius(
    topRight : Float = 0f,
    topLeft : Float = 0f,
    bottomRight: Float = 0f,
    bottomLeft : Float = 0f
){
    this.shapeAppearanceModel = this.shapeAppearanceModel
        .toBuilder()
        .setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
        .setTopRightCorner(CornerFamily.ROUNDED, topRight)
        .setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
        .setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
        .build()
}

fun View.toTransitionGroup() = this to transitionName