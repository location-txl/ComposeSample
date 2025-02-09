package com.location.compose.sample.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

var isStatic = false
var compositionLocalName = ""
val currentLocalColor = if (isStatic) {
    compositionLocalName = "StaticCompositionLocal 场景"
    staticCompositionLocalOf { Color.Black }
} else {
    compositionLocalName = "DynamicCompositionLocal 场景"
    compositionLocalOf { Color.Black }
}

var recomposeFlag = "Init"
@Preview
@Composable
fun CompositionLocalDemo() {
    var color by remember{ mutableStateOf(Color.Green) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "${compositionLocalName}")
            Spacer (Modifier.height(20.dp))
            CompositionLocalProvider(
                currentLocalColor provides color
            ) {
                val a = 1
                TaggedBox("Wrapper: ${recomposeFlag}", 400.dp,Color.Red) {
                    TaggedBox("Middle: ${recomposeFlag}", 300.dp, currentLocalColor.current) {
                        TaggedBox("Inner: ${recomposeFlag}", 200.dp, Color.Yellow)
                    }
                }
            }
            Spacer (Modifier.height(20.dp))
            Button(
                onClick = {
                    color = Color.Blue
                }
            ) {
                Text(text = "Change Theme")
            }
        }
    }
    recomposeFlag = "Recompose"
}

@Composable
private fun TaggedBox(tag:String, size: Dp, background: Color, content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .size(size)
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = tag)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}