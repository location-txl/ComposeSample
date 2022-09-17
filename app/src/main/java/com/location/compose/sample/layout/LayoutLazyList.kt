package com.location.compose.sample.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.bottom.LazyListCommon
import com.location.compose.sample.bottom.LazyListItemKey
import com.location.compose.sample.bottom.LazyListStickyHeader
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 *
 * @author tianxiaolong
 * time：2022/9/15 20:30
 * description：
 */
@Composable
fun LayoutLazyList(
    back: () -> Unit,
    navigateRotate: (String) -> Unit
) {
    TitleBar(title = "LazyList", back = back) {
        Column {
            Text(
                text = "LazyList代表可滑动并支持复用的列表， 在Compose中 有LazyColumn LazyRow " +
                        "对应AndroidView中为RecyclerView设置LinearLayoutManager为VERTICAL和HORIZONTAL"
            )
            Text(text = "LazyRow和LazyColumn使用方法相同 不另写sample")
            LazyColumn(
                contentPadding = PaddingValues(10.dp) //设置内容间距
            ) {
                item {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navigateRotate(LazyListCommon.routeName)
                        }) {
                        Text(text = "普通使用layoutColumn")
                    }
                }
                item {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navigateRotate(LazyListStickyHeader.routeName)
                        }) {
                        Text(text = "layoutColumn吸顶")
                    }
                }
                item {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navigateRotate(LazyListItemKey.routeName)
                        }) {
                        Text(text = "layoutColumn itemKey")
                    }
                }
            }
        }


    }
}

private val colors = listOf(Color.Blue, Color.Green, Color.Yellow)

@Composable
fun LayoutLazyListCommon(back: () -> Unit) {
    TitleBar(title = "LazyColumn普通写法", back = back) {
        Box {
            val lazyListState = rememberLazyListState()
            var rememberCoroutineScope = rememberCoroutineScope()
            Column(modifier = Modifier.fillMaxSize()) {
                val dataList = remember {
                    mutableStateListOf<Item>().apply {

                        var colorIndex = 0
                        for (i in 0..1000) {
                            if (colorIndex >= colors.size) {
                                colorIndex = 0
                            }
                            add(Item("content:$i", colors[colorIndex++]))
                        }
                    }
                }
                Row {
                    Button(onClick = {
                        with(dataList) {
                            val oldSize = size
                            clear()
                            val newSize = Random.nextInt(oldSize / 2, oldSize * 2)
                            var colorIndex = 0
                            for (i in 0..newSize) {
                                if (colorIndex >= colors.size) {
                                    colorIndex = 0
                                }
                                add(Item("content:$i", colors[colorIndex++]))
                            }
                        }
                    }) {
                        Text(text = "更新列表")
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),//添加每个item之间的间隔
                    state = lazyListState,
                ) {
                    items(dataList, key = {
                        it.text
                    }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(it.color),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(text = it.text)
                            }
                        }
                    }

                }
            }
            val showFab by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex > 0
                }
            }
            AnimatedVisibility(
                visible = showFab, modifier = Modifier
                    .padding(end = 10.dp, bottom = 20.dp)
                    .align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    onClick = {
                        rememberCoroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = "")
                }
            }
        }
    }
}

private data class Item(val text: String, val color: Color)

@Preview
@Composable
fun PreviewLayoutLazyListCommon() {
    LayoutLazyListCommon {

    }
}