package com.death.monkey.domain

import android.graphics.drawable.Drawable

data class Apps(val name:String, val packageName:String, val enabled:Boolean=false, val icon:Drawable)