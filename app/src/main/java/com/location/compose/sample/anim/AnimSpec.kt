package com.location.compose.sample.anim

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