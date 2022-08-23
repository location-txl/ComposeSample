package com.location.compose.sample.weight

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.location.compose.sample.R
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/12 19:26
 * description：
 */
@Composable
fun WeightText(back: () -> Unit) {

    TitleBar(title = "文本", back = back) {
        Column {
            Text(text = "粗体文本", fontWeight = FontWeight.Bold)
            Text(text = "细点的", fontWeight = FontWeight.Medium)
            Text(text = "斜体文本", fontStyle = FontStyle.Italic)
            Text(text = "粗斜体文本", fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
            Text(
                text = "下划线文字",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                textDecoration = TextDecoration.Underline
            )
            Text(
                text = "删除线文字",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                textDecoration = TextDecoration.LineThrough
            )
            Text(buildAnnotatedString {
                append("富文本默认文字：")
                withStyle(style = SpanStyle(Color.Red)) {
                    append("红色文本")
                }
                append(" 正常文字")
                withStyle(SpanStyle(fontSize = 30.sp)){
                    append("大的文字")
                }
            })
            Text(text = stringResource(id = R.string.weight_text))
            //超长文字...
            Text(
                text = "《狂人日记》是鲁迅创作的第一个短篇白话文日记体小说，也是中国第一部现代白话小说，写于1918年4月。该文首发于1918年5月15日4卷5号的《新青年》月刊，后收入《呐喊》集，编入《鲁迅全集》第一卷",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断超长文字直接截断",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

            Card(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text("居中对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Text("右对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right)
                    Text("左对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Left)
                }
            }

            SelectionContainer {
                Row {
                    Text(text = "可以复制的文本")
                    DisableSelection {
                        Text(text = "中间不可复制")
                    }
                    Text(text = "可以复制的文本2")
                }
            }

            Text(text = "第一行 修改行间距 \n第二行", lineHeight = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightText() {
    WeightText {

    }
}