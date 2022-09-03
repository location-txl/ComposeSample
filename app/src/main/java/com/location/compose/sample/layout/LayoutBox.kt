package com.location.compose.sample.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/9/3 18:11
 * description：
 */

@Composable
fun LayoutBoxSample(back: () -> Unit) {
    TitleBar(title = "Box", back = back) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Box 类似于xml中的FrameLayout",
                style = MaterialTheme.typography.body1
            )
            Box {
                BackgroundWeight(color = Color.Blue, modifier = Modifier.size(300.dp))
                BackgroundWeight(color = Color.Green, modifier = Modifier.size(200.dp))
                BackgroundWeight(color = Color.Gray, modifier = Modifier.size(150.dp))
                Button(onClick = {  }) {
                    Text(text = "按钮")
                }

            }
        }

    }
}

@Composable
private fun BackgroundWeight(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color)) {
    }
}