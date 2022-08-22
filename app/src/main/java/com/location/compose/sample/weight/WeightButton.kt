package com.location.compose.sample.weight

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.ButtonState
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/12 17:02
 * description：
 */

@Composable
fun WeightButton(back: () -> Unit) {
    TitleBar(title = "按钮", back = back){
        Column {
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context, "点击按钮", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "普通按钮")
            }

            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "点击按钮FloatingActionButton", Toast.LENGTH_SHORT)
                        .show()
                }, modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            ) {
                Text(text = "浮动按钮")
            }

            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, null)
            }

            Button(onClick = { }) {
                Icon(Icons.Default.Search, null)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "带图标的按钮")
            }


            Button(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(),
            ) {
                Text(text = "自定义按钮")
            }
            val interactionState = remember { MutableInteractionSource() }
            val (text, textColor, buttonColor) = when {
                interactionState.collectIsPressedAsState().value -> ButtonState(
                    "按下状态",
                    Color.Red,
                    Color.Black
                )
                else -> ButtonState("正常状态", Color.White, Color.Red)
            }
            Button(
                onClick = { },
                interactionSource = interactionState,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                ),
                modifier = Modifier.padding(10.dp),
            ) {
                Text(text = text, color = textColor)
            }
        }
    }

}