package com.location.compose.sample.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.location.compose.sample.R
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/10/31 19:50
 * description：
 */


private const val TIME_LEN = 3000

/**
 * fadeIn 淡入淡出
 * slideIn x y 偏移
 * expandIn 裁切
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilitySample(back: () -> Unit) {
    TitleBar(title = "AnimatedVisibility", back = back) {
        Column {
            var show by remember {
                mutableStateOf(false)
            }
            /*
            slideIn(
                animationSpec = tween(TIME_LEN)
            ) {
                IntOffset(it.width * 2, it.height * 2)
            }
             */


            /**
             * animationSpec 动画曲线
             * expandFrom 从哪个位置开始
             * clip 是否裁切 即超出部分是否显示 如expandFrom设置为 Alignment.Center后 上方的部分是否显示
             * initialSize 返回一个IntSize 动画初始大小
             */
/*            expandIn(
                animationSpec = tween(TIME_LEN),
                expandFrom =  Alignment.BottomStart,
                clip = false
            ) {
                IntSize(it.width, it.height/3)
            }*/
            AnimatedVisibility(
                label = "1",
                visible = show,
                enter = scaleIn(
                    animationSpec = tween(TIME_LEN),
                    initialScale = 0.3f, //初始的缩放百分比
                    transformOrigin = TransformOrigin(0.1f, 0.1f) //缩放的中心点 0.5 0.5 就是正中心
                ),
            ) {
                //自定义动画
                val alpha by transition.animateFloat(
                    transitionSpec = { tween(TIME_LEN + 1000) },
                    label = "custom") {
                   when(it){
                       EnterExitState.PreEnter -> 0.0f
                       EnterExitState.Visible -> 1.0f
                       EnterExitState.PostExit -> 0.0f
                   }
                }
                Image(
                    painter = painterResource(id = R.drawable.cat),
                    modifier = Modifier.alpha(alpha),
                    contentDescription = "cat")
            }

            /*AnimatedVisibility(
                label = "2",
                visible = show,
                enter = expandIn(
                    animationSpec = tween(TIME_LEN),
                    expandFrom =  Alignment.Center,
                    clip = true
                ) {
                    IntSize(it.width, it.height/3)
                },
            ) {
                Image(painter = painterResource(id = R.drawable.cat), contentDescription = "cat")
            }*/
            Button(onClick = {
                show = !show
            }) {
                Text(text = "点击切换")
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun AnimatedVisibilitySamplePreview() {
    AnimatedVisibilitySample {

    }
}