package com.pru.ricknmorty.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import android.view.Window
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.pru.ricknmorty.appContext


object Global {

    fun getString(@StringRes id: Int): String {
        return appContext.getString(id)
    }

    fun Drawable.drawableToBitmap(): Bitmap? {
        val drawable = this
        val bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val targetBmp: Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
        val canvas = Canvas(targetBmp)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    fun Bitmap?.getDominantColor(colorCallback: (Color?) -> Unit) {
        if (this == null) {
            colorCallback.invoke(null)
            return
        }
        Palette.from(this).generate { palette ->
            val vibrant: Int? = palette?.getVibrantColor(0x000000)
            vibrant?.let {
                colorCallback.invoke(Color(it))
            }
        }
    }

    // start of extension.
}