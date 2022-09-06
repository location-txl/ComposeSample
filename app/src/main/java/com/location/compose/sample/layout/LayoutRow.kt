package com.location.compose.sample.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.BackgroundWeight
import com.location.compose.sample.common.HorizontalDivide
import com.location.compose.sample.common.TitleBar
import com.location.compose.sample.scope.radioGroupScopeOf

/**
 *
 * @author tianxiaolong
 * time：2022/9/5 17:06
 * description：
 */


@Composable
inline fun LayoutRowHomeSample(
    noinline back: () -> Unit,
    crossinline navigateRotate: (String) -> Unit
) {
    TitleBar(title = "Row", back = back) {
        Column {
            Text(
                text = buildAnnotatedString {
                    append("Row 类似于xml中的")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("LinearLayout")
                    }
                    append("设置")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("orientation=horizontal")
                    }
                },
                style = MaterialTheme.typography.body1
            )

            val list = remember {
                listOf("普通使用Row", "Row权重")
            }
            LazyColumn {
                itemsIndexed(list) { index, it ->
                    Button(onClick = {
                        when (index) {
                            0 -> navigateRotate(com.location.compose.sample.bottom.Row.routeName)
                            1 -> navigateRotate(com.location.compose.sample.bottom.RowWeight.routeName)
                        }

                    }) {
                        Text(text = it)
                    }
                }
            }

        }

    }
}

@Composable
fun LayoutRowSample(back: () -> Unit) {
    TitleBar(title = "普通的Row", back = back) {
        Column {
            Text(text = "指定方向的Row 可以在Row的函数参数内使用horizontalArrangement verticalAlignment来指定子View的方向 子View也可以使用Modifier.align来单独控制单个子View的方向")
            HorizontalDivide()
            Text(text = "设置horizontalArrangement参数")
            val horList = remember {
                listOf("Start", "Center", "end", "SpaceEvenly", "SpaceBetween", "SpaceAround")
            }

            fun convertHorState(state: String) = when (state) {
                "Start" -> Arrangement.Start
                "Center" -> Arrangement.Center
                "end" -> Arrangement.End
                "SpaceEvenly" -> Arrangement.SpaceEvenly
                "SpaceBetween" -> Arrangement.SpaceBetween
                "SpaceAround" -> Arrangement.SpaceAround
                else -> Arrangement.Start
            }

            var horizontalArrangement by remember {
                mutableStateOf(convertHorState(horList[0]))
            }
            RowRadioGroup(horList) {
                horizontalArrangement = convertHorState(it)
            }
            val verList = remember {
                listOf("top", "CenterVertically", "Bottom")
            }

            fun convertVerState(state: String) = when (state) {
                "top" -> Alignment.Top
                "CenterVertically" -> Alignment.CenterVertically
                "Bottom" -> Alignment.Bottom
                else -> Alignment.Top
            }

            var verticalAlignment by remember {
                mutableStateOf(convertVerState(verList[0]))
            }
            Text(text = "设置verticalAlignment参数")
            RowRadioGroup(list = verList) {
                verticalAlignment = convertVerState(it)
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
            ) {
                BackgroundWeight(color = Color.Blue, modifier = Modifier.size(100.dp))
                BackgroundWeight(color = Color.Red, modifier = Modifier.size(100.dp))
            }

        }

    }
}

@Composable
fun LayoutRowWeightSample(back: () -> Unit) {
    TitleBar(title = "Row权重", back = back) {
        Column {
            Text(text = "Row的权重可以根据比例均分子view")
            Row(modifier = Modifier.fillMaxSize()) {
                BackgroundWeight(
                    color = Color.Blue, modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                )
                BackgroundWeight(
                    color = Color.Red, modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutRowSamplePreview() {
    LayoutRowSample {
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutRowWeightSamplePreview() {
    LayoutRowWeightSample {

    }
}

@Composable
fun RowRadioGroup(list: List<String>, onSelectChangeListener: (String) -> Unit) {

    Row(
        Modifier
            .padding(5.dp)
            .horizontalScroll(state = rememberScrollState())
    ) {
        var horSelectIndex by remember(list) {
            mutableStateOf(0)
        }
        val horRadioGroupScope = remember(list) {
            radioGroupScopeOf {
                horSelectIndex = it
                onSelectChangeListener(list[horSelectIndex])
            }
        }
        list.forEach { title ->
            horRadioGroupScope.Item { checked, onClick ->
                Row(
                    modifier = Modifier.clickable {
                        onClick()
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = checked, onClick = {
                        onClick()
                    })
                    Text(text = title)
                }
            }
        }

    }
}


