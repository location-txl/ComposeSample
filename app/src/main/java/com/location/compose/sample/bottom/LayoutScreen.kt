package com.location.compose.sample.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.location.compose.sample.layout.LayoutBoxSample

/**
 *
 * @author tianxiaolong
 * time：2022/9/1 18:47
 * description：
 */
sealed class LayoutScreen(
    val name: String,
    val routeName: String = "Layout/$name",
    val content: @Composable (
        back: () -> Unit
    ) -> Unit
) {
    companion object {
        const val START = "Layout/home"
        val LayoutItems = listOf(Box, Row, Column)
    }
}


private object Box : LayoutScreen("box", content = { back ->
    LayoutBoxSample(back)
})

private object Row : LayoutScreen("row", content = { back ->
    Payload(back)
})

private object Column : LayoutScreen("column", content = { back ->
    Payload(back)
})


@Composable
private inline fun Payload(back: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "占位界面", textAlign =  TextAlign.Center)
    }
}


