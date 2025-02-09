package com.location.compose.sample.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
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
import com.location.compose.sample.bottom.LazyListItemType
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

                item {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navigateRotate(LazyListItemType.routeName)
                        }) {
                        Text(text = "layoutColumn itemType")
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LayoutLazyListStickHeader(back: () -> Unit) {
    TitleBar(title = "Lazy", back = back) {
        Column {
            Text(text = "LazyColumn可以很方便的实现stickyHeader吸顶效果 常见如手机的通讯录")
            val dataList = remember {
                val names = listOf("小明", "小红", "小蓝", "小光", "小丽")
                val contactList = mutableListOf<Contact>()
                for (name in  names){
                    contactList.add(Contact.ContactHeader(name))
                    for(i in 1..10){
                        contactList.add(Contact.ContactUser("${name}_i"))
                    }
                }
                contactList
            }
            LazyColumn() {
                dataList.forEach { contact ->
                    when(contact){
                        is Contact.ContactHeader -> {
                            stickyHeader {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                                    .padding(10.dp)
                                ) {
                                    Text(text = contact.name, color = Color.White)
                                }
                            }
                        }
                        is Contact.ContactUser -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(Color.Cyan.copy(0.8f)),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(text = contact.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun LayoutLazyListItemType(back: () -> Unit) {
    TitleBar(title = "LazyColumn ItemType", back = back) {
        Column {
            Text(text = "LazyColumn 在配置item时可以设置itemType 更好的让compose进行复用 提升流畅度")
            val dataList = remember {
                val names = listOf("小明", "小红", "小蓝", "小光", "小丽")
                val contactList = mutableListOf<Contact>()
                for (name in  names){
                    contactList.add(Contact.ContactHeader(name))
                    for(i in 1..10){
                        contactList.add(Contact.ContactUser("${name}_i"))
                    }
                }
                contactList
            }
            LazyColumn{
                dataList.forEach { contact ->
                    when(contact){
                        is Contact.ContactHeader -> {
                            item(contentType = { 1 }) { //注意这里的contentType
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                                    .padding(10.dp)
                                ) {
                                    Text(text = contact.name, color = Color.White)
                                }
                            }
                        }
                        is Contact.ContactUser -> {
                            item(contentType = { 2 }) {//注意这里的contentType
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(Color.Cyan.copy(0.8f)),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(text = contact.name)
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}


@Composable
fun LayoutLazyListItemKey(back: () -> Unit) {

    TitleBar(title = "LazyColumn itemKey", back = back) {
        Column {
            Text(
                text = "当我们在使用LazyColumn时，如果我们的item是动态变化的，那么我们就需要使用itemKey来保证item的唯一性 否则由于导致item的复用，导致item的状态混乱 itemKey的值可以是任意的，但是必须保证itemKey的值是唯一的"
            )
            var addIndex = remember { 2000 }
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

            Button(onClick = {
                dataList.add(1, Item("content:${addIndex++}", colors[addIndex%3]))
            }) {
                Text(text = "添加item到第一项")
            }

            LazyColumn {
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
                            Text(text = "状态由data维护")
                            TextField(value = it.content.value, onValueChange = {
                                newText ->
                                it.content.value = newText
                            })
                            Text(text = "状态由item维护 item变化时会导致item的复用 状态混乱 添加itemKey后item变化时状态不会混乱")
                            var text by remember { mutableStateOf("") }
                            TextField(value = text, onValueChange = {
                                    newText ->
                                text = newText
                            })
                        }
                    }
                }
            }

        }
    }
}

private sealed class Contact() {
    data class ContactHeader(val name: String) : Contact()
    data class ContactUser(val name: String) : Contact()
}


private data class Item(val text: String, val color: Color, val content:MutableState<String> = mutableStateOf(""))


@Preview
@Composable
fun PreviewLayoutLazyListCommon() {
    LayoutLazyListCommon {

    }
}


@Preview
@Composable
fun PreviewLayoutStickHeader() {
    LayoutLazyListStickHeader {

    }
}

@Preview
@Composable
fun PreviewLayoutLazyListItemType() {
    LayoutLazyListItemType {

    }
}