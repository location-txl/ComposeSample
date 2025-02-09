package com.location.compose.sample.anim

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.launch

/**
 * animateDpAsState 内部也是使用 Animatable 实现的 animateDpAsState提升了便携性 针对的是状态切换
 * animateDpAsState 不能设置初始值 Animatable可以设置初始值
 * TwoWayConverter官方支持了Dp Float Int Rect DpOffset Size Offset IntOffset IntSize
 * 如果需要自定义 需要实现TwoWayConverter接口
 * @see ageConverter
 */
@Composable
fun AnimatableSample(back: () -> Unit) {
    TitleBar(title = "Animatable", back = back) {
        Column {
            val anim = remember {
                Animatable(100.dp, Dp.VectorConverter)
            }
            val coroutineScope = rememberCoroutineScope()
            Box(modifier = Modifier.size(anim.value).background(Color.Green))
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (anim.value == 100.dp) {
                            anim.animateTo(200.dp)
                        } else {
                            anim.animateTo(100.dp, block = {
                                //动画每帧的回调
                                Log.d("AnimatableSample", "value=$value")
                            })
                        }
                    }
                }) {
                Text(text = "使用动画完成大小的变化")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (anim.value == 100.dp) {
                            anim.snapTo(200.dp)
                        } else {
                            anim.snapTo(100.dp)
                        }
                    }
                }) {
                Text(text = "瞬间完成大小的变化 不使用动画")
            }
            /**
             * 自定义AnimationVector
             */
            val animAge = remember {
                Animatable(Age(20), ageConverter)
            }
            TextButton(onClick = {
                coroutineScope.launch {
                    if (animAge.value.age == 20) {
                        animAge.animateTo(Age(80))
                    } else {
                        animAge.animateTo(Age(20))
                    }
                }
            }) {
                Text(text = "年龄: ${animAge.value.age}")
            }

        }
    }
}

private data class Age(val age: Int)

/**
 * 自定义TwoWayConverter
 * AnimationVector1D 这是一个参数 还可以有两个参数AnimationVector2D 三个参数AnimationVector3D
 */
private val ageConverter = object : TwoWayConverter<Age, AnimationVector1D> {
    /**
     * 将Age转换为AnimationVector1D
     */
    override val convertFromVector: (AnimationVector1D) -> Age
        get() = { Age(it.value.toInt()) }

    /**
     * 将AnimationVector1D转换为Age
     */
    override val convertToVector: (Age) -> AnimationVector1D
        get() = { AnimationVector1D(it.age.toFloat()) }

}

@Preview
@Composable
fun AnimatableSamplePreview() {
    AnimatableSample{

    }
}
