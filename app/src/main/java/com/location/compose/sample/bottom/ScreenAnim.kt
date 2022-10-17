package com.location.compose.sample.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.location.compose.sample.anim.*

/**
 *
 * @author tianxiaolong
 * time：2022/9/1 18:47
 * description：
 */
sealed class ScreenAnim(
    val name: String,
    val routeName: String = "Anim/$name",
    val subScreen: List<ScreenAnim>? = null,
    val content: @Composable (
        subScreen: List<ScreenAnim>?,
        back: () -> Unit,
        navigateRotate: (String) -> Unit
    ) -> Unit
) {
    companion object {
        const val START = "Anim/home"
        val AnimItems:List<ScreenAnim> = listOf(ScreenAnimAnimateXxxAsState, ScreenAnimAnimatable, ScreenAnimAnimationSpec)
    }
}

private object ScreenAnimAnimateXxxAsState : ScreenAnim("AnimateXxxAsState", content = { _,back, _ ->
    AnimXXXStateSample(back)
})

private object ScreenAnimAnimatable : ScreenAnim("Animatable", content = { _, back, _ ->
    AnimatableSample(back)
})


private object ScreenAnimAnimationSpec : ScreenAnim("AnimationSpec", subScreen = listOf(ScreenSpecTweenSpec, ScreenSpecSnapSpec, ScreenSpecKeyFrameSpec), content = {subScreen, back, nav ->
    AnimSpecHomeSample(subScreen!!, back, nav)
})

private object ScreenSpecTweenSpec:ScreenAnim("TweenSpec",content = {_, back, _ ->
    AnimTweenSpecSample(back)
})

private object ScreenSpecSnapSpec:ScreenAnim("SnapSpec",content = {_, back, _ ->
    AnimSnapSpecSample(back)
})

private object ScreenSpecKeyFrameSpec:ScreenAnim("KeyFrameSpec",content = {_, back, _ ->
    AnimKeyFrameSpecSample(back)
})





@Composable
private inline fun Payload(back: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "占位界面", textAlign =  TextAlign.Center)
    }
}

