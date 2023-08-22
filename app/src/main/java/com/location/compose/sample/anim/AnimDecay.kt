package com.location.compose.sample.anim

import android.widget.OverScroller
import androidx.compose.animation.core.*
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 衰减 消散型动画
 * 列表松手后的动画
 * [splineBasedDecay] 适用于px  Android惯性滑动曲线方案[OverScroller] ListView RecyclerView
 * [exponentialDecay] 指数型曲线 他不会针对不同的设备进行修正
 * [exponentialDecay.frictionMultiplier] 摩擦力系数 默认为1 越大摩擦力越大
 * [exponentialDecay.absVelocityThreshold] 速度阈值 默认为0.1f 即什么时候认为动画结束
 * 如果为0的话 动画永远不会结束 但是会越来越慢 Compose规定了不能为0
 *
 */
@Composable
fun AnimDecaySample(back: () -> Unit) {
    TitleBar(title = "Decay", back = back) {
        Column {
            Text(text = "Animatable#animateDecay 用于衰减动画 比如列表松手后的动画")
            val offsetAnim = remember {
                Animatable(0f)
            }
            val coroutineScope = rememberCoroutineScope()
            val splineBasedDecay = rememberSplineBasedDecay<Float>()
//            exponentialDecay<>()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = 0.dp, y = offsetAnim.value.dp)
                .background(Color.Green))

            var enable by remember {
                mutableStateOf(true)
            }

            var velocity by remember {
                mutableStateOf(1000f)
            }
            var job:Job? = remember {
                null
            }
            TextField(value = velocity.toString(),
                onValueChange = {
                    val temp = it.toFloat()
                    job?.let { delayJob ->
                        delayJob.cancel()
                        job = null
                    }
                    if (temp < 1000f) {
                        job =  coroutineScope.launch {
                            delay(1000)
                            if(velocity < 1000f){
                                velocity = 1000f
                            }
                        }
                    }
                    velocity = temp
                },
                leadingIcon = {
                    Text(text = "初始速度:")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                enabled = enable,
                onClick = {
                    coroutineScope.launch {
                        enable = false
                        offsetAnim.animateDecay(velocity, splineBasedDecay)
                        enable = true
                    }
                }) {
                Text(text = "AnimDecay动画")
            }

            Button(
                enabled = enable,
                onClick = {
                    coroutineScope.launch {
                        enable = false
                        offsetAnim.snapTo(0f)
                        enable = true
                    }
                }) {
                Text(text = "重置")
            }
        }

    }

}


@Preview
@Composable
fun AnimDecaySamplePreview() {
    AnimDecaySample{

    }
}