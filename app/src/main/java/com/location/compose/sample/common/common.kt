package com.location.compose.sample.common

import android.R.attr.navigationIcon
import android.R.attr.padding
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.location.compose.sample.R
import com.location.compose.sample.scope.radioGroupScopeOf

/**
 *
 * @author tianxiaolong
 * time：2022/7/20 15:56
 * description：
 */



@Composable
fun SearchEdittext() {
    var text by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF101324))
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF3F51B5), RoundedCornerShape(20.dp))
                .padding(top = 10.dp, bottom = 10.dp)
                .weight(1.0f)
        ) {
            BasicTextField(value = text,
                onValueChange = {
                    text = it
                },
                decorationBox = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(painterResource(R.drawable.search_icon), null)
                        Box(Modifier.weight(1f)) {
                            it()
                        }
                        if (text.isNotEmpty()) {
                            Image(
                                painterResource(R.drawable.delete_icon),
                                null,
                                modifier = Modifier
                                    .clickable {
                                        text = ""
                                    })
                        }
                    }
                }
            )
        }
        Text(
            text = "取消",
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable {
                    text = ""
                },
            color = Color.White
        )
    }
}


inline fun LazyListScope.itemTitle(
    title: String,
    crossinline block: @Composable LazyItemScope.() -> Unit
) {
    item {
        Text(
            text = title,
            style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 10.dp)
        )
        block()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(title: String, back: () -> Unit, content: @Composable () -> Unit) {
    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            IconButton(onClick = {
                back()
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        }


//        TopAppBar(title = {
//            Text(title)
//        },
//            navigationIcon = {
//            IconButton(onClick = {
//                back()
//            }) {
//                Icon(Icons.Filled.ArrowBack, null)
//            }
//        })
        Box {
            content()
        }
    }
}


@Composable
fun CheckBoxText(
    text: String,
    initChecked: Boolean,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled:Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    var value by remember(initChecked) {
        mutableStateOf(initChecked)
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = value,
            enabled = enabled,
            interactionSource = interactionSource,
            onCheckedChange = {
                value = it
                onCheckedChange(it)
            }
        )
        Text(text = text, color = LocalContentColor.current.copy(alpha = if (enabled) 1f else 0.5f))
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxText() {
    CheckBoxText("checkbox", false) {
        Log.d("txlTest", "checkbox:$it")
    }
}


@Composable
fun IconCheckBox(
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    checkStyle: IconCheckBoxStyle = LocalIconCheckStyle.current,
    space: Dp? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: (Boolean) -> Unit
) {
    val nowSpace = space ?: checkStyle.space
    IconButton(
        onClick = {
            onCheckedChange(!checked)
        },
        modifier = modifier,
        interactionSource = interactionSource,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painterResource(id = if (checked) LocalIconCheckStyle.current.checkedRes else LocalIconCheckStyle.current.uncheckedRes),
                null
            )
            Spacer(modifier = Modifier.size(width = nowSpace, height = 0.dp))
            Text(text = text, color = LocalContentColor.current.copy(alpha = if (checked) 1f else 0.5f))
        }

    }
}


@Composable
fun BackgroundWeight(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color))
}

@Composable
fun HorizontalDivide(
    modifier: Modifier = Modifier,
    style: DivideStyle = LocalHorDivideStyle.current
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = style.padding, bottom = style.padding)
            .height(style.size)
            .background(style.color)
    )
}

@Composable
fun VerticalDivide(
    modifier: Modifier = Modifier,
    style: DivideStyle = LocalHorDivideStyle.current
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = style.padding, end = style.padding)
            .width(style.size)
            .background(style.color)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewIconCheckBox() {
    var checked by remember {
        mutableStateOf(false)
    }
    IconCheckBox(checked, "选择框1"){
        checked = it
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRadioGroupScopeTest() {
    val scope = remember {
        radioGroupScopeOf(1) {
            Log.d("txl", "select index:$it")
        }
    }
    Column {
        with(scope){
            Item { checked, onClick ->
                RadioButton(selected = checked, onClick = onClick)
            }
            Item { checked, onClick ->
                RadioButton(selected = checked, onClick = onClick)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewTitleBar() {
    TitleBar("标题", {}, {
        Column {
            Text(text = "内容")
        }
    })


}




@Preview(showBackground = true)
@Composable
fun searchEdittextPreview() {
    SearchEdittext()
}


@Preview(showBackground = true)
@Composable
fun testEdittext() {
    LazyColumn {
        items(35) {
            Text(
                text = "占位符$it",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            var text by remember {
                mutableStateOf("")
            }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "TextField")
                }
            )
        }

        item {
            var text by remember {
                mutableStateOf("")
            }
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(Color.Red),
            )
        }
    }
}



data class ButtonState(val text: String, val textColor: Color, val backgroundColor: Color)


data class IconCheckBoxStyle(@DrawableRes val checkedRes: Int, @DrawableRes val uncheckedRes: Int, val space: Dp = 2.dp)

data class DivideStyle(val size:Dp, val padding:Dp, val color: Color)


val LocalIconCheckStyle = staticCompositionLocalOf { IconCheckBoxStyle(R.drawable.check_box_checked, R.drawable.check_box_normal) }

val LocalHorDivideStyle = compositionLocalOf {
    DivideStyle(3.dp, 5.dp, Color.Gray.copy(alpha = 0.5f))
}


