package com.location.compose.sample.recombination

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.location.compose.sample.common.randomColor


/**
 * 如果发生重组 颜色会发生变化
 *
 */
@Preview
@Composable
fun TestInline(){
    Column {
        var count by remember {
            mutableIntStateOf(1)
        }

        Button(onClick = {
            count++
        }) {
            Column {
                Text(text = "count:${count}", color = Color.randomColor)
                Text(text = "noCount", color = Color.randomColor)
            }
        }
        Text(text = "hello compose", color = Color.randomColor)
        Text(text = "hello count:$count", color = Color.randomColor)
    }
}