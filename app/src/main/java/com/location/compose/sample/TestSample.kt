package com.location.compose.sample

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Preview
@Composable
fun Test() {

}

@Preview
@Composable
fun TestLayout() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Gray)) {
        Box(
            Modifier
                .layout { measurable, constraints ->
                    Log.d("txl2D", "1")
                    Log.d("txl2D", "100dp:${100.dp.roundToPx()}")
                    val start = 120.dp.roundToPx()
                    val top = 100.dp.roundToPx()
                    val p2 = measurable.measure(constraints)
                    Log.d("txl2D", "before w:${p2.width} h:${p2.height}")
                    val pl = measurable.measure(constraints.offset(-start, -top))
                    Log.d("txl2D", "af w:${pl.width} h:${pl.height}")
                    val width = constraints.constrainWidth(pl.width + start)
                    val height = constraints.constrainHeight(pl.height + top)
                    layout(width, height) {
                        pl.placeRelative(start, top)
                    }
                }
                .layout { measurable, constraints ->
                    Log.d("txl2D", "2")
                    val p = measurable.measure(constraints)
                    layout(p.width, p.height) {
                        p.placeRelative(0, 0)
                    }
                }
                .size(200.dp)
                .background(Color.Red)
        )
    }
}


private class PaddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    init {
        require(
            (start.value >= 0f || start == Dp.Unspecified) &&
                    (top.value >= 0f || top == Dp.Unspecified) &&
                    (end.value >= 0f || end == Dp.Unspecified) &&
                    (bottom.value >= 0f || bottom == Dp.Unspecified)
        ) {
            "Padding must be non-negative"
        }
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        //获取横向的偏移值
        val horizontal = start.roundToPx() + end.roundToPx()
        //获取纵向的偏移值
        val vertical = top.roundToPx() + bottom.roundToPx()
        //获取view的测量值
        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            if (rtlAware) {
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + top.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + bottom.hashCode()
        result = 31 * result + rtlAware.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? PaddingModifier ?: return false
        return start == otherModifier.start &&
                top == otherModifier.top &&
                end == otherModifier.end &&
                bottom == otherModifier.bottom &&
                rtlAware == otherModifier.rtlAware
    }
}