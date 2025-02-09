package com.location.compose.sample.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.location.compose.sample.common.TitleBar

/**
 * compose版本的ConstraintLayout
 * 需要app/build.gradle中添加    implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"
 */
@Composable
fun LayoutConstraintLayout(back: () -> Unit) {
    TitleBar(title = "ConstraintLayout", back = back) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (button1, button2) = createRefs()
            Button(modifier = Modifier.constrainAs(button1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }, onClick = { }) {
                Text(text = "按钮1")
            }

            Button(modifier = Modifier.constrainAs(button2) {
                start.linkTo(button1.end, 20.dp)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, onClick = { }) {
                Text(text = "按钮2")
            }

            val (button3, button4, button5) = createRefs()
            createHorizontalChain(button3, button4, button5, chainStyle = ChainStyle.SpreadInside)
            Button(modifier = Modifier
                .constrainAs(button3) {
                top.linkTo(button1.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(button4.start)
            }, onClick = { }) {
                Text(text = "按钮3")
            }

            Button(modifier = Modifier.constrainAs(button4) {
                top.linkTo(button3.top)
                start.linkTo(button3.end)
                end.linkTo(button5.start)
            }, onClick = { }) {
                Text(text = "按钮4")
            }

            Button(modifier = Modifier.constrainAs(button5) {
                top.linkTo(button3.top)
                start.linkTo(button4.end)
                end.linkTo(parent.end)
            }, onClick = { }) {
                Text(text = "按钮5")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLayoutConstraintLayout() {
    LayoutConstraintLayout {

    }
}

