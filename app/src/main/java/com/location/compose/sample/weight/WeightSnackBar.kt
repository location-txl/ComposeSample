package com.location.compose.sample.weight

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
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
        val scaffoldState = rememberScaffoldState()
        val rememberCoroutineScope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { snackbarHostState ->
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(snackbarData = it)
                }
            }) {
            Column {
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
}

@Preview(showBackground = true)
@Composable
fun SnackBarPreview() {
    WeightSnackBar {

    }
}