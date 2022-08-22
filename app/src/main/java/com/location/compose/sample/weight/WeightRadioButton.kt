package com.location.compose.sample.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.location.compose.sample.common.TitleBar
import com.location.compose.sample.scope.radioGroupScopeOf

/**
 *
 * @author tianxiaolong
 * time：2022/8/18 19:47
 * description：
 */
@Composable
fun WeightRadioButton(back: () -> Unit) {
    TitleBar(title = "单选按钮", back = back) {
        Column {
            Text(text = "官方的单选按钮", style = MaterialTheme.typography.h6)
            var select by remember {
                mutableStateOf(false)
            }
            RadioButton(selected = select, onClick = {
                select = !select
            })
            Text(text = "自定义RadioGroup作用域", style = MaterialTheme.typography.h6)
            val radioGroupScope = remember {
               radioGroupScopeOf {

               }
            }
            Row {
                with(radioGroupScope){
                    for(i in 1..3){
                        Item {checked, onClick ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = checked, onClick = onClick)
                                Text(text = "单选按钮$i")
                            }
                        }
                    }
                }
            }

            Text(text = "自定义RadioGroup作用域 文字也可点击 并默认选择索引2", style = MaterialTheme.typography.h6)
            val radioGroupScope2 = remember {
                radioGroupScopeOf(2) {

                }
            }
            Row {
                with(radioGroupScope2){
                    for(i in 1..3){
                        Item {checked, onClick ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                                onClick()
                            }) {
                                RadioButton(selected = checked, onClick = onClick)
                                Text(text = "单选按钮$i")
                            }
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightRadioButton() {
    WeightRadioButton{

    }
}