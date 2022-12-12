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
inline fun LayoutColumnHomeSample(
    noinline back: () -> Unit,
    crossinline navigateRotate: (String) -> Unit
) {
    TitleBar(title = "Column", back = back) {
        Column {
            Text(
                text = buildAnnotatedString {
                    append("Column 类似于xml中的")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("LinearLayout")
                    }
                    append("设置")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("orientation=vertical")
                    }
                },
                style = MaterialTheme.typography.body1
            )

            val list = remember {
                listOf("普通使用Column", "Column权重")
            }
            LazyColumn {
                itemsIndexed(list) { index, it ->
                    Button(onClick = {
                        when (index) {
                            0 -> navigateRotate(com.location.compose.sample.bottom.Column.routeName)
                            1 -> navigateRotate(com.location.compose.sample.bottom.ColumnWeight.routeName)
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
fun LayoutColumnSample(back: () -> Unit) {
    TitleBar(title = "普通的Column", back = back) {
        Column {
            Text(text = "指定方向的Column 可以在Column的函数参数内使用verticalArrangement horizontalAlignment来指定子View的方向 子View也可以使用Modifier.align来单独控制单个子View的方向")
            HorizontalDivide()
            Text(text = "设置verticalArrangement参数")
            val horList = remember {
                listOf("Top", "Center", "Bottom", "SpaceEvenly", "SpaceBetween", "SpaceAround")
            }

            fun convertHorState(state: String) = when (state) {
                "Top" -> Arrangement.Top
                "Center" -> Arrangement.Center
                "Bottom" -> Arrangement.Bottom
                "SpaceEvenly" -> Arrangement.SpaceEvenly
                "SpaceBetween" -> Arrangement.SpaceBetween
                "SpaceAround" -> Arrangement.SpaceAround
                else -> Arrangement.Top
            }

            var verticalArrangement by remember {
                mutableStateOf(convertHorState(horList[0]))
            }
            RowRadioGroup(horList) {
                verticalArrangement = convertHorState(it)
            }
            val verList = remember {
                listOf("Start", "CenterHorizontally", "End")
            }

            fun convertVerState(state: String) = when (state) {
                "Start" -> Alignment.Start
                "CenterHorizontally" -> Alignment.CenterHorizontally
                "End" -> Alignment.End
                else -> Alignment.Start
            }

            var horizontalAlignment by remember {
                mutableStateOf(convertVerState(verList[0]))
            }
            Text(text = "设置horizontalAlignment参数")
            RowRadioGroup(list = verList) {
                horizontalAlignment = convertVerState(it)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
            ) {
                BackgroundWeight(color = Color.Blue, modifier = Modifier.size(100.dp))
                BackgroundWeight(color = Color.Red, modifier = Modifier.size(100.dp))
            }

        }

    }
}

@Composable
fun LayoutColumnWeightSample(back: () -> Unit) {
    TitleBar(title = "Column权重", back = back) {
        Column {
            Text(text = "Column的权重可以根据比例均分子view")
            Column(modifier = Modifier.fillMaxSize()) {
                BackgroundWeight(
                    color = Color.Blue, modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                )
                BackgroundWeight(
                    color = Color.Red, modifier = Modifier
                        .weight(0.3f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutColumnSamplePreview() {
    LayoutColumnSample {
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutColumnWeightSamplePreview() {
    LayoutColumnSample {

    }
}

@Composable
private fun ColumnRadioGroup(list: List<String>, onSelectChangeListener: (String) -> Unit) {

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


