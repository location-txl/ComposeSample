package com.location.compose.sample.anim

import android.animation.Keyframe
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.location.compose.sample.bottom.ScreenAnim
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.launch


@Composable
fun AnimSpecHomeSample(subScreen:List<ScreenAnim>, back: () -> Unit, navigateRotate: (String) -> Unit) {
    TitleBar(title = "AnimationSpec", back = back) {
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Center,
            content = {
                items(subScreen) { item ->
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        OutlinedButton(
                            onClick = {
                                navigateRotate(item.routeName)
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp)
                        ) {
                            Text(text = item.name)
                        }
                    }
                }
            })

    }

}

/**
 * Tween 补间动画
 * [LinearEasing] 线性曲线
 * [FastOutSlowInEasing] 快出慢进 先加速后减速 元素从A到B 一直都在 没有隐藏 没有显示
 * [LinearOutSlowInEasing] 一直减速 用于从不可见到可见的元素
 * [FastOutLinearInEasing] 一直加速 用于从可见到不可见的元素
 * https://cubic-bezier.com/
 */
@Composable
fun AnimTweenSpecSample(back: () -> Unit) {
    TitleBar(title = "TweenSpec", back = back) {
     Column {
         val anim = remember {
             Animatable(0.dp,Dp.VectorConverter)
         }
         val scope = rememberCoroutineScope()
         Box(modifier = Modifier
             .offset(x = anim.value, y = 0.dp)
             .size(100.dp)
             .background(Color.Green))
         Button(onClick = {
             scope.launch {
                 anim.animateTo(if(anim.value == 0.dp) 200.dp else 0.dp, TweenSpec(300, easing = FastOutSlowInEasing))
             }
         }) {
             Text(text = "FastOutSlowInEasing 先加速后减速")
         }

         Button(onClick = {
             val customEasing = CubicBezierEasing(0.17f,0.67f, 1f, -0.38f)
             val cE = Easing { fraction ->
                 /**
                  * [fraction] 时间完成度
                  * @return 动画完成度
                  */
                 fraction
             }
             scope.launch {
                 anim.animateTo(if(anim.value == 0.dp) 200.dp else 0.dp, TweenSpec(300, easing = customEasing))
             }
         }) {
             Text(text = "使用自定义的动画曲线")
         }

         val animVis = remember {
             Animatable((-100).dp,Dp.VectorConverter)
         }
         Box(modifier = Modifier
             .offset(x = animVis.value, y = 0.dp)
             .size(100.dp)
             .background(Color.Green))
         Button(onClick = {
             scope.launch {
                 val targetValue = if(animVis.value == 50.dp) (-100).dp else 50.dp
                 animVis.animateTo(targetValue, TweenSpec(2000, easing = if(targetValue == 50.dp) LinearOutSlowInEasing else FastOutLinearInEasing))
             }
         }) {
             Text(text = "LinearOutSlowInEasing 一直减速 \n FastOutLinearInEasing 一直加速")
         }



     }
        
    }
}

/**
 * [SnapSpec] 顺间完成的动画 和[Animatable.snapTo]的作用一样 不同的时[SnapSpec]可以设置延时
 */
@Composable
fun AnimSnapSpecSample(back: () -> Unit) {
    TitleBar(title = "SnapSpec", back = back) {
        Column {
            val animOffset = remember {
                Animatable(0.dp,Dp.VectorConverter)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = animOffset.value, y = animOffset.value)
                .background(Color.Green))
            Button(onClick = {
                coroutineScope.launch {
                    val targetValue = if(animOffset.value == 0.dp) 200.dp else 0.dp
                    animOffset.animateTo(targetValue, SnapSpec(delay = 1000))
                }
            }) {
                Text(text = "SnapSpec动画")
            }
        }
    }
}

/**
 * [KeyframesSpec] 可以配置关键帧 和原生View[Keyframe]差不多
 * 具体数值 at 时间(毫秒) width 动画曲线
 * @param back Function0<Unit>
 */
@Composable
fun AnimKeyFrameSpecSample(back: () -> Unit){

    TitleBar(title = "KeyFrameSpec", back = back) {
        Column{
            val animOffset = remember {
                Animatable(0.dp,Dp.VectorConverter)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = animOffset.value, y = animOffset.value)
                .background(Color.Green))
            Button(onClick = {
                coroutineScope.launch {
                    val targetValue = if(animOffset.value == 0.dp) 200.dp else 0.dp
                    animOffset.animateTo(
                        targetValue,
                        KeyframesSpec(KeyframesSpec.KeyframesSpecConfig<Dp>().apply {
                            durationMillis = 2000
                            val easing = FastOutLinearInEasing
                            if(targetValue == 200.dp){
                                0.dp at 0 with easing
                                100.dp at 500 with easing
                                300.dp at 1500 with easing
                                200.dp at 2000 with easing
                            }else{
                                200.dp at 0 with easing
                                190.dp at 500 with easing
                                (-50).dp at 1500 with easing
                                0.dp at 2000 with easing
                            }

                        })
                    )
                }
            }) {
                Text(text = "KeyFrameSpec动画")
            }
        }
    }
}

@Composable
fun AnimRepeatableSpecSample(back: () -> Unit){
    TitleBar(title = "RepeatableSpec", back = back) {
        Column{
            val animOffset = remember {
                Animatable(0.dp,Dp.VectorConverter)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = animOffset.value, y = animOffset.value)
                .background(Color.Green))
            Button(onClick = {
                coroutineScope.launch {
                    val targetValue = if(animOffset.value == 0.dp) 200.dp else 0.dp
                    animOffset.animateTo(
                        targetValue,
                        RepeatableSpec(
                            iterations = 3,
                            repeatMode = RepeatMode.Reverse,
                            animation = TweenSpec(1000, easing = FastOutSlowInEasing)
                        )
                    )
                }
            }) {
                Text(text = "RepeatableSpec动画")
            }
        }
    }
}

/**
 * [InfiniteRepeatableSpec] 无限循环动画
 * [RepeatMode.Reverse] 从头到尾再从尾到头
 * [RepeatMode.Restart] 从头到尾再从头到尾
 * [InfiniteRepeatableSpec.initialStartOffset] 设置动画延迟
 * [StartOffsetType.Delay] 延迟多少毫秒再开始
 * [StartOffsetType.FastForward] 快进到多少毫秒再开始
 */
@Composable
fun AnimInfiniteRepeatableSpecSample(back: () -> Unit){
    TitleBar(title = "InfiniteRepeatableSpec", back = back) {
        Column{
            val animOffset = remember {
                Animatable(0.dp,Dp.VectorConverter)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = animOffset.value, y = animOffset.value)
                .background(Color.Green))
            Button(onClick = {
                coroutineScope.launch {
                    val targetValue = if(animOffset.value == 0.dp) 200.dp else 0.dp
                    animOffset.animateTo(
                        targetValue,
                        infiniteRepeatable(
                            animation = TweenSpec(1000, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                }
            }) {
                Text(text = "InfiniteRepeatableSpec动画")
            }
        }
    }
}

/**
 * [SpringSpec] 弹簧物理模型动画
 * [SpringSpec.dampingRatio] 阻尼系数 0-1 越小弹的越厉害
 * [SpringSpec.stiffness] 刚度系数 越大越有力 越快
 * [SpringSpec.visibilityThreshold] 可见性阙值 默认是0.1 代表动画到什么程度结束
 * [Animatable.animateTo] 里面的initialVelocity 代表的是初始速度
 */
@Composable
fun AnimSpringSpecSample(back: () -> Unit) {

    TitleBar(title = "SpringSpec", back = back) {
        Column{
            val animOffset = remember {
                Animatable(0.dp,Dp.VectorConverter)
            }
            var enable by remember {
                mutableStateOf(true)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier
                .size(100.dp)
                .offset(x = animOffset.value, y = animOffset.value)
                .background(Color.Green))
            Button(
                enabled = enable,
                onClick = {
                coroutineScope.launch {
                    val targetValue = if(animOffset.value == 0.dp) 200.dp else 0.dp
                    enable = false
                    animOffset.animateTo(
                        targetValue,
                        spring(
                            dampingRatio = 0.1f,
                            stiffness = Spring.StiffnessHigh
                        )
                    )
                    enable = true
                }
            }) {
                Text(text = "SpringSpec动画")
            }
        }
    }
}

@Preview
@Composable
fun AnimTweenSpecPreview() {
    AnimTweenSpecSample{

    }
}

@Preview
@Composable
fun AnimSnapSpecSamplePreview() {
    AnimSnapSpecSample{

    }
}

@Preview
@Composable
fun AnimKeyFrameSpecSamplePreview() {
    AnimKeyFrameSpecSample{

    }
}

@Preview
@Composable
fun AnimRepeatableSpecSamplePreview() {
    AnimRepeatableSpecSample{

    }
}

@Preview
@Composable
fun AnimInfiniteRepeatableSpecSamplePreview() {
    AnimInfiniteRepeatableSpecSample{

    }
}

@Preview
@Composable
fun AnimSpringSpecSamplePreview() {
    AnimSpringSpecSample{

    }
}