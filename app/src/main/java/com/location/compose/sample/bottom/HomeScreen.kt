package com.location.compose.sample.bottom

import androidx.annotation.StringRes
import com.location.compose.sample.R

/**
 *
 * @author tianxiaolong
 * time：2022/7/20 15:44
 * description：
 */
sealed class HomeScreen(val rotateName:String, @StringRes val titleId:Int){
    object Weight : HomeScreen("home/weight_graph", R.string.screen_weight)
    object Layout : HomeScreen("home/layout", R.string.screen_layout)
    object Anim : HomeScreen("home/anim", R.string.screen_anim)
    object Modifier : HomeScreen("home/modifier", R.string.screen_other)
}


