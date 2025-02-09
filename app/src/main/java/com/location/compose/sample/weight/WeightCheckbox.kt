package com.location.compose.sample.weight

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.CheckBoxText
import com.location.compose.sample.common.IconCheckBox
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/15 14:16
 * description：
 */
@Composable
fun WeightCheckbox(back: () -> Unit) {
    TitleBar(title = "CheckBox", back = back) {
        Column {
            CheckBoxText("复选框1", initChecked = true){

            }

            CheckBoxText("复选框2", initChecked = false){

            }

            Text(text = "自定义复选框", style = MaterialTheme.typography.titleMedium)
            var checked by remember {
                mutableStateOf(false)
            }
            IconCheckBox(checked, "复选框3", space = 2.dp) {
                checked = it
            }
        }
    }
}

@Preview
@Composable
fun PreviewCheckBox() {
    WeightCheckbox {

    }
}