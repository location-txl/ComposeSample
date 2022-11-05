package com.location.compose.sample.anim

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar

/**
 * 通过一个状态设置多组动画
 * @author tianxiaolong
 * time：2021/3/22 10:48 PM
 * description：
 */
@Composable
fun AnimTransitionSample(back: () -> Unit) {
    TitleBar(title = "Transition", back = back) {
        var big by remember {
            mutableStateOf(false)
        }
        val transition = updateTransition(targetState = big, label = "big")
        val color by transition.animateColor(
            //设置spec
            transitionSpec = {
                if (false isTransitioningTo true) {
                    //从小到大
                    tween(durationMillis = 200)
                } else {
                    //从大到小
                    tween(durationMillis = 500)
                }
            },
            label = "bgColor"
        ) {
            if (it) {
                Color.Red
            } else {
                Color.Blue
            }
        }

        val size by transition.animateDp(label = "size") {
            if (it) {
                200.dp
            } else {
                100.dp
            }
        }

        val round by transition.animateDp(label = "radius") {
            if (it) {
                100.dp
            } else {
                0.dp
            }
        }
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(round))
                .background(color = color)
                .clickable {
                    big = !big
                }
        )
    }

}

@Preview
@Composable
fun AnimTransitionSamplePreview() {
    AnimTransitionSample{

    }
}