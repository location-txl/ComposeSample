package com.location.compose.sample.anim

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch


/**
 * [Animatable.stop] 停止动画
 * [Animatable.snapTo] [Animatable.animateTo] [Animatable.animateDecay] 只会有一个起作用 当调用时会停止之前的动画
 * 使用stop 或者其他开启动画 导致的动画打断都会触发[CancellationException]
 *
 * [Animatable.updateBounds] 可以设置动画的上界和下界 当动画到达边界值时就会停止
 *
 * [AnimationResult.endReason] [Animatable.animateTo] [Animatable.animateDecay] 动画的返回值
 * 代表动画是到达边界停止了 还是到达了targetValue
 * @see AnimationEndReason.Finished 动画自然完成 到达了targetValue
 * @see AnimationEndReason.BoundReached 动画到达了边界值
 */
@Composable
fun AnimStateSample(back: () -> Unit) {


    TitleBar(title = "动画状态", back = back) {
        BoxWithConstraints {
            Column {
                val animOffset = remember {
                    Animatable(0.dp, Dp.VectorConverter).apply {
                        /**
                         * 设置动画下界 到达这个值动画就会停止
                         */
                        updateBounds(upperBound = (this@BoxWithConstraints.maxHeight - 100.dp))
                    }

                }

                val coroutineScope = rememberCoroutineScope()
                Box(modifier = Modifier
                    .size(100.dp)
                    .offset(x = 0.dp, y = animOffset.value)
                    .background(Color.Green))
                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            val state = animOffset.animateTo(
                                animOffset.value + 100.dp,
                                tween(durationMillis = 3000)
                            )
                            if(state.endReason == AnimationEndReason.Finished){
                                println("动画完成")
                            }else{
                                println("动画到达边界结束")
                            }
                        }catch (e: CancellationException) {
                            println("动画被打断")
                        }
                    }
                }) {
                    Text(text = "开始动画")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        animOffset.snapTo(0.dp)
                    }

                }) {
                    Text(text = "重置动画")
                }

                Button(onClick = {
                    coroutineScope.launch {
                        animOffset.stop()
                    }
                }) {
                    Text(text = "停止动画")
                }
            }
        }
    }

}