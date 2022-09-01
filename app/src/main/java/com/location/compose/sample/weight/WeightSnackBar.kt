package com.location.compose.sample.weight

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.TitleBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * @author tianxiaolong
 * time：2022/8/25 20:18
 * description：
 */
@Composable
fun WeightSnackBar(back: () -> Unit) {

    TitleBar(
        title = "SnackBar",
        back = back,
    ) {
        Column {
            CommonSnackBar(modifier = Modifier.weight(1f))
            CustomSnackBar(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun CustomSnackBar(modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    val rememberCoroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "消息:${it.message}")
                    Spacer(modifier = Modifier.width(10.dp))
                    Row() {
                        TextButton(
                            onClick = { it.dismiss() }) {
                            Text(text = "取消")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        TextButton(
                            onClick = { it.performAction() }) {
                            Text(text = it.actionLabel?:"知道了")
                        }
                    }
                }
            }
        }
    ) {

        Button(onClick = {
            rememberCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("消息")
            }

        }) {
            Text(text = "显示自定义SnackBar")
        }
    }
}

@Composable
private fun CommonSnackBar(modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    val rememberCoroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        snackbarHost = { snackbarHostState ->
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(snackbarData = it)
            }
        }) {
        Column {
            Text(text = "官方的snackBar")
            var status by remember {
                mutableStateOf(0)
            }
            Button(
                enabled = status == 0,
                onClick = {
                    status = 1
                    rememberCoroutineScope.launch {
                        val result =
                            scaffoldState.snackbarHostState.showSnackbar(
                                "snackBar",
                                actionLabel = "按钮"
                            )
                        status = if (result == SnackbarResult.ActionPerformed) 2 else 3
                        delay(1500)
                        status = 0
                    }

                }) {
                Text(text = "显示SnackBar")
            }
            val statusMsg = when (status) {
                1 -> "点击snackBar"
                2 -> "点击snackBar按钮"
                3 -> "snackBar自动消失"
                else -> "未知状态"
            }
            Text(text = "snackBar 状态:$statusMsg")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SnackBarPreview() {
    WeightSnackBar {

    }
}