package com.location.compose.sample.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.location.compose.sample.R
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2022/8/15 11:29
 * description：
 */
@Composable
fun WeightImage(back: () -> Unit) {
    TitleBar(title = "图片", back = back) {
        val rememberScrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(rememberScrollState)) {
            Text(text = "加载资源图片", style = MaterialTheme.typography.h6)
            Image(painter = painterResource(id = R.drawable.image), contentDescription = "删除图片")
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "删除图片",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(100.dp)
                    .background(Color.Red),
                contentScale = ContentScale.None
            )
            Text(text = "加载网络图片", style = MaterialTheme.typography.h6)
            AsyncImage(
                model = "https://t7.baidu.com/it/u=3569419905,626536365&fm=193&f=GIF",
                contentDescription = null
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeightImage() {
    WeightImage{

    }
}