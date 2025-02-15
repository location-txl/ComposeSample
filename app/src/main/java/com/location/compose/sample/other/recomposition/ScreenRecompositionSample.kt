package com.location.compose.sample.other.recomposition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.HorizontalDivide
import com.location.compose.sample.common.TitleBar
import com.location.compose.sample.common.randomColor


@Composable
fun ScreenRecompositionSample(back: () -> Unit){
    TitleBar("重组", back = back) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)){
            readStateComposition()
            InlineComposition()
            ManyComposable()
            NoStableDataComposable()
        }
    }
}


/**
* 读取 State 的组合 在 State 变化时，会发生重组
 * 没有读取 State 的组合，不会发生重组
 */
@Composable
private fun readStateComposition(){
    Box(modifier = Modifier
        .size(100.dp)
        .randomColor()) {
        var count by remember { mutableIntStateOf(0) }
        Button(
            onClick = {
            count++
        }) {
            Text(text = "count:$count", color = randomColor)
        }
    }
}


/**
*  内联代码在编译时会被复制到调用它的地方
 * Column 是内联的 所以这块代码编译完可能是这样的
 * ```
 * column start
 * Text
 * Button
 * column end
 * ```
 *
 * 所以 Text实际和 Column 在同一个组合中 Text 读取了 count 变量，会导致 Column 和 Text 都触发重组
 */
@Composable
private fun InlineComposition() {
    HorizontalDivide()
    var count by remember { mutableIntStateOf(0) }

    Text("内联重组", style = MaterialTheme.typography.titleMedium)
    Column(modifier = Modifier
        .fillMaxWidth()
        .randomColor()) {
        Text(text = "count:$count", color = randomColor)
        Button(
            onClick = {
                count++
            }
        ) {
            Text(text = "修改 count")
        }
    }
}


/**
* 在同一层级下时 使用Composable函数 隔离重组作用域
 */
@Composable
fun ManyComposable(modifier: Modifier = Modifier) {
    HorizontalDivide()
    var count by remember { mutableIntStateOf(0) }


    Text("使用Composable 隔离重组作用域", style = MaterialTheme.typography.titleMedium)
    Text(text = "在同一层级下 将不同的控件抽成不同的组合 可以避免不必要的重组")
    Column(modifier = Modifier
        .fillMaxWidth()
        .randomColor()
        .padding(20.dp)
    ) {
        //使用 Composable 隔离重组作用域
        NoComposable()
        ComposableParam("hello")
        ComposableParam("value-$count")
        Text(text = "count:$count", color = randomColor)
        Button(
            onClick = {
                count++
            }
        ) {
            Text(text = "修改 count")
        }
    }
}

@Composable
private fun NoComposable() {
    Text(text = "不需要重组", color = randomColor)
}

@Composable
private fun ComposableParam(value: String){
    Text(text = "value:$value", color = randomColor)
}

@Composable
private fun NoStableDataComposable(){
    var userData = remember { UserData("hello") }
    HorizontalDivide()
    Text("不稳定的数据会导致一直重组 无法跳过重组", style = MaterialTheme.typography.titleMedium)
    var count by remember { mutableIntStateOf(0) }
    Button(
        onClick = {
            count++
            userData.name = "value-$count"
        }
    ) {
        Text(text = "修改 count$count")
    }


}


private data class UserData(var name: String)

private fun Modifier.randomColor() = this.then(Modifier.background(randomColor))