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
        val AnimItems: List<ScreenAnim> = listOf(
            ScreenAnimAnimateXxxAsState,
            ScreenAnimAnimatable,
            ScreenAnimAnimationSpec,
            ScreenAnimDecay,
            ScreenAnimState,
            ScreenAnimTransition,
            ScreenAnimatedVisibility,
            ScreenAnimCrossfade,
            ScreenAnimContent,
        )
    }
}

private object ScreenAnimAnimateXxxAsState : ScreenAnim("AnimateXxxAsState", content = { _,back, _ ->
    AnimXXXStateSample(back)
})

private object ScreenAnimAnimatable : ScreenAnim("Animatable", content = { _, back, _ ->
    AnimatableSample(back)
})


private object ScreenAnimAnimationSpec : ScreenAnim("AnimationSpec",
    subScreen = listOf(
        ScreenSpecTweenSpec,
        ScreenSpecSnapSpec,
        ScreenSpecKeyFrameSpec,
        ScreenRepeatableSpec,
        ScreenInfiniteRepeatableSpec,
        ScreenSpringSpec,
    ), content = { subScreen, back, nav ->
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

private object ScreenRepeatableSpec:ScreenAnim("RepeatableSpec",content = {_, back, _ ->
    AnimRepeatableSpecSample(back)
})

private object ScreenInfiniteRepeatableSpec:ScreenAnim("InfiniteRepeatableSpec",content = {_, back, _ ->
    AnimInfiniteRepeatableSpecSample(back)
})

private object ScreenSpringSpec:ScreenAnim("SpringSpec",content = {_, back, _ ->
    AnimSpringSpecSample(back)
})


private object ScreenAnimDecay:ScreenAnim("Decay", content = {_, back, _ ->
    AnimDecaySample(back)
})

private object ScreenAnimState:ScreenAnim("动画状态", routeName = "Anim/state", content = { _, back, _ ->
    AnimStateSample(back)
})


private object ScreenAnimatedVisibility:ScreenAnim("AnimatedVisibility", content = {_, back, _ ->
    AnimatedVisibilitySample(back)
})

private object ScreenAnimCrossfade:ScreenAnim("Crossfade", content = {_, back, _ ->
    AnimatedCrossfadeSample(back)
})

private object ScreenAnimContent:ScreenAnim("AnimatedContent", content = {_, back, _ ->
    AnimatedContentSample(back)
})

private object ScreenAnimTransition:ScreenAnim("Transition", content = {_, back, _ ->
    AnimTransitionSample(back)
})







@Composable
private inline fun Payload(back: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "占位界面", textAlign =  TextAlign.Center)
    }
}


