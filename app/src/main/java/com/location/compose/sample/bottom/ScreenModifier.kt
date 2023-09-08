package com.location.compose.sample.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.location.compose.sample.modifier.ScreenModifierParseSample
import com.location.compose.sample.other.ScreenCompositionLocalSample

/**
 *
 * @author tianxiaolong
 * time：2022/9/1 18:47
 * description：
 */
sealed class ScreenModifier(
    val name: String,
    val routeName: String = "Modifier/$name",
    val subScreen: List<ScreenModifier>? = null,
    val content: @Composable (
        subScreen: List<ScreenModifier>?,
        back: () -> Unit,
        navigateRotate: (String) -> Unit
    ) -> Unit
) {
    companion object {
        const val START = "Modifier/home"
        val ModifierItems: List<ScreenModifier> = listOf(
            ScreenParseModifier,
            ScreenCompositionLocal,
        )
    }
}

private object ScreenParseModifier :
    ScreenModifier("Modifier解析",
        routeName = "Modifier/parse",
        content = { _, back, _ ->
            ScreenModifierParseSample(back)
        })

private data object ScreenCompositionLocal:
    ScreenModifier("CompositionLocal",
        routeName = "Modifier/compositionLocal",
        content = { _, back, _ ->
            ScreenCompositionLocalSample(back)
        })







@Suppress("unused", "UNUSED_PARAMETER")
@Composable
private inline fun Payload(back: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "占位界面", textAlign =  TextAlign.Center)
    }
}


