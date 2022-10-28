package com.elkfrawy.ontime.presentation.util

import android.view.View

object ViewUtil {

    fun View.show(){
        visibility = View.VISIBLE
    }

    fun View.hide(){
        visibility = View.GONE
    }

}