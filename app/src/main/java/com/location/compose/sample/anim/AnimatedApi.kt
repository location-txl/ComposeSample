package com.location.compose.sample.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
                    label = "custom"
                ) {
                    when (it) {
                        EnterExitState.PreEnter -> 0.0f
                        EnterExitState.Visible -> 1.0f
                        EnterExitState.PostExit -> 0.0f
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.cat),
                    modifier = Modifier.alpha(alpha),
                    contentDescription = "cat"
                )
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

@Composable
fun AnimatedCrossfadeSample(back: () -> Unit) {
    TitleBar(title = "Crossfade", back = back) {

        Column {
            Text(
                text = """
                    Crossfade 用于切换两个不同的组件
                    通过targetState来切换
                    不可以配置动画参数 动画参数为淡入淡出
                """.trimIndent(),
                style = MaterialTheme.typography.body1,
            )
            var showRed by remember {
                mutableStateOf(true)
            }
            Crossfade(
                showRed,
                label = "变换动画",
                animationSpec = tween(3000),
            ) {
                if(it){
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Red)
                    )
                }else{
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.Blue)
                    )
                }
            }
            Button(onClick = { showRed = !showRed }) {
                Text(text = "变换")
            }
        }
    }
}


/**
 * [Crossfade]的高级版本 可以配置动画属性 以及两个布局的前后顺序
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSample(back: () -> Unit) {
    TitleBar(title = "AnimatedContent", back = back) {
        Column {
            Text(
                text ="""
                    对内容的变化进行动画处理
                    可以自定义内容进入和退出的动画参数
                """.trimIndent(),
                style = MaterialTheme.typography.body1,
            )

            var showRed by remember {
                mutableStateOf(true)
            }

            AnimatedContent(
                showRed,
                label = "testAnim",
                /**
                 * 配置动画参数
                 * with前是入场动画
                 * with后是出场动画
                 */
                transitionSpec = {
                    (slideIn(
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    ) {
                        if(true isTransitioningTo false){
                            IntOffset(0, -it.height)
                        }else{
                            IntOffset(0, it.height)
                        }
                    }  + fadeIn(tween(durationMillis = 1500, easing = LinearEasing))).togetherWith(
                        slideOut(
                            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                        ) {
                            if (true isTransitioningTo false) {
                                IntOffset(0, it.height)
                            } else {
                                IntOffset(0, -it.height)
                            }
                        } + fadeOut(tween(durationMillis = 1500, easing = LinearEasing)))
                }

                ) {
                if(it){
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = "cat",
                        modifier = Modifier.size(200.dp)
                    )
                }else{
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = "cat",
                        modifier = Modifier.size(200.dp)
                    )
                }

            }
            Button(onClick = { showRed = !showRed }) {
                Text(text = "变换")
            }

            var num by remember {
                mutableIntStateOf(0)
            }

            AnimatedContent(
                targetState = num,
                label = "num",
                transitionSpec = {
                    slideIn(
                        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
                    ){
                        IntOffset(0, it.height)
                    } togetherWith slideOut(
                        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
                    ){
                        IntOffset(0, -it.height)
                    }
                }
            ) {
                Text(text = "num = $it", modifier = Modifier.padding(10.dp))
            }
            Button(onClick = {
                num++
            }) {
                Text(text = "++")
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


@Preview
@Composable
fun AnimatedCrossfadeSamplePreview() {
    AnimatedCrossfadeSample {

    }
}


@Preview
@Composable
fun AnimatedContentSamplePreview() {
    AnimatedContentSample {

    }
}