package com.location.compose.sample.weight

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/23 14:56
 * description：
 */
@Composable
fun WeightSlider(back: () -> Unit) {

    TitleBar(title = "Slider", back = back) {
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            var progress by remember {
                mutableStateOf(0f)
            }
            val interactionState = remember { MutableInteractionSource() }
            val text = if (interactionState.collectIsDraggedAsState().value) {
                "正在调节进度"
            } else {
                "没有在调节进度"
            }
            Text(text = text)
            Slider(
                value = progress,
                onValueChange = {
                    progress = it
//                    Log.d("txlTest", "progress:$it")
                },
                onValueChangeFinished = {
//                    Log.d("txlTest", "onValueChangeFinished")
                },
                valueRange = 0f..100f,
                steps = 0,//总共steps+1段 为0则无级调节
                interactionSource = interactionState,
            )
            Row {
                Button(onClick = {
                    if (progress < 100) {
                        progress++
                    }
                }) {
                    Text(text = "进度++")
                }
                Button(
                    modifier = Modifier.padding(start = 20.dp),
                    onClick = {
                        if (progress > 0) {
                            progress--
                        }
                    }) {
                    Text(text = "进度--")
                }
            }

            Text(text = "自定义进度条", modifier = Modifier.padding(top = 20.dp))
            var progress2 by remember {
                mutableStateOf(0f)
            }
            Slider(
                value = progress2,
                onValueChange = {
                    progress2 = it
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,//滑块颜色
                    activeTrackColor = Color.Green,//激活已经过的进度条颜色
                )
            )
            var progress3 by remember {
                mutableStateOf(0f)
            }
            Slider(
                value = progress3,
                onValueChange = {
                    progress3 = it
                },
                valueRange = 0f..100f,
                steps = 5,//总共steps+1段 为0则无级调节
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Green,
                    inactiveTrackColor = Color.Black.copy(alpha = 0.5f), //激活未经过的进度条颜色
                    activeTickColor = Color.Yellow, //刻度颜色
                )
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightSlider() {
    WeightSlider {

    }
}