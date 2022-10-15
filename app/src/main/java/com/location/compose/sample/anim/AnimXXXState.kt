package com.location.compose.sample.anim

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar

/**
 * animateDpAsState 内部也是使用 Animatable 实现的 animateDpAsState提升了便携性 针对的是状态切换
 */
@Composable
fun AnimXXXStateSample(back: () -> Unit) {
    TitleBar(title = "AnimXXXState", back = back) {
        Column {
            var buttonSize by remember {
                mutableStateOf(100.dp)
            }
            val buttonAnim by animateDpAsState(buttonSize, finishedListener = {
                    Log.d("AnimXXXStateSample", "finishedListener: $it")
            })

            Button(modifier = Modifier.size(buttonAnim), onClick = {
                buttonSize = if(buttonSize == 100.dp) 200.dp else 100.dp
            }) {
                Text(text = "")
            }

            var num by remember {
                mutableStateOf(0)
            }
            val numAnim by animateIntAsState(targetValue = num)
            Button(onClick = {
                num = if(num == 0) 200 else 0
            }) {
                Text(text = "num: $numAnim")
            }
        }
    }
}

@Preview
@Composable
fun AnimXXPreview() {
    AnimXXXStateSample {

    }
}