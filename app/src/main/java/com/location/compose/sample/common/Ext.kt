package com.location.compose.sample.common

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

//val Color.randnmColor:Color
//    get() {
//        color
//    }

val randomColor
    get() = Color(Random.nextInt(0,255),Random.nextInt(0,255),Random.nextInt(0,255))
