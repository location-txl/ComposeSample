package com.location.compose.sample.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.location.compose.sample.bottom.LazyListCommon
import com.location.compose.sample.bottom.LazyListItemKey
import com.location.compose.sample.bottom.LazyListStickyHeader
import com.location.compose.sample.common.TitleBar

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

@Composable
fun LayoutLazyListCommon(back: () -> Unit,) {
    TitleBar(title = "LazyColumn普通写法", back = back) {

    }
}