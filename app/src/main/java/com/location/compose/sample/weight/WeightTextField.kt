package com.location.compose.sample.weight

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.location.compose.sample.common.SearchEdittext
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/12 19:29
 * description：
 */
@Composable
fun WeightTextField(back:() -> Unit) {
    TitleBar(title = "输入框", back = back) {
        Column {
            var text1 by remember {
                mutableStateOf("")
            }
            TextField(value = text1,
                modifier = Modifier.padding(bottom = 10.dp),
                placeholder = {
                    //设置提示文字
                    Text(text = "最基本的输入框")
                },
                onValueChange = {
                    text1 = it
                })
            var text1_1 by remember {
                mutableStateOf("")
            }
            TextField(value = text1_1,
                modifier = Modifier.padding(bottom = 10.dp),
                label = {
                    //设置提示文字
                    Text(text = "提示文字2.0")
                },
                onValueChange = {
                    text1_1 = it
                })

            var text2 by remember {
                mutableStateOf("")
            }
            TextField(value = text2,
                modifier = Modifier.padding(bottom = 10.dp),
                placeholder = {
                    //设置提示文字
                    Text(text = "只能输入数字")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    text2 = it
                })
            Text(text = "leadingIcon 可以设置左边的内容")
            var text3 by remember {
                mutableStateOf("")
            }
            TextField(value = text3,
                modifier = Modifier.padding(bottom = 10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                onValueChange = {
                    text3 = it
                })

            Text(text = "trailingIcon 可以设置右边的内容")
            var text4 by remember {
                mutableStateOf("")
            }
            val context = LocalContext.current
            TextField(value = text4,
                modifier = Modifier.padding(bottom = 10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                trailingIcon = {
                    Icon(Icons.Default.Send, null, Modifier.clickable {

                        Toast.makeText(context, "点击发送按钮", Toast.LENGTH_SHORT).show()
                    })
                },
                onValueChange = {
                    text4 = it
                })

            var text5 by remember {
                mutableStateOf("")
            }
            var text5Vis by remember {
                mutableStateOf(false)
            }
            //设置密码框
            TextField(value = text5,
                modifier = Modifier.padding(bottom = 10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                label = {
                    //设置提示文字
                    Text(text = "请输入密码")
                },
                trailingIcon = {
                    Button(onClick = { text5Vis = !text5Vis }) {
                        Text(text = if (text5Vis) "隐藏" else "显示")
                    }
                },
                //显示模式
                visualTransformation = if (text5Vis) {
                    VisualTransformation.None
                } else {
                    //这里设置密码框的显示模式
                    PasswordVisualTransformation(mask = '*')
                },
                //输入英文和字符
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                singleLine = true,
                onValueChange = {
                    text5 = it.trim()
                })

            Text(text = "BaseTextField 更多自定义")
            SearchEdittext()
            Text(text = "OutlinedTextField")
            var text6 by remember {
                mutableStateOf("")
            }
            var isError by remember {
                mutableStateOf(false)
            }
            OutlinedTextField(
                value = text6,
                onValueChange = {
                    text6 = it
                    isError = text6.length > 6
                },
                isError = isError,
                label = {
                    //设置提示文字
                    Text(text = "输入6个字符以内")
                },
            )
        }
    }
}