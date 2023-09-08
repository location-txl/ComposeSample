package com.location.compose.sample.other

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.location.compose.sample.common.CheckBoxText
import com.location.compose.sample.common.HorizontalDivide
import com.location.compose.sample.common.TitleBar

/**
 *
 * @author tianxiaolong
 * time：2023/9/4 21:05
 * description：
 */
var boxColor = Color.Red
@Composable
fun ScreenCompositionLocalSample(back: () -> Unit) {
    TitleBar(title = "CompositionLocal使用", back = back) {
        val state = rememberScrollState()

        Column(
            modifier = Modifier.verticalScroll(state)
        ) {
            Text(text = "CompositionLocal解决了什么问题"
                , style = MaterialTheme.typography.h6)
            Text(text = "很多时候我们需要在 composable 树中共享一些数据（例如主题配置），一种有效方式就是通过显式参数传递的方式进行实现，当参数越来越多时，composable 参数列表会变得越来越臃肿，难以进行维护。当 composable 需要彼此间传递数据，并且实现各自的私有性时，如果仍采用显式参数传递的方式则可能会产生意料之外的麻烦与崩溃。",
                style = MaterialTheme.typography.body1)
            HorizontalDivide()
            Text(text = "为解决上述痛点问题， Jetpack Compose 提供了 CompostionLocal 用来完成 composable 树中共享数据方式。CompositionLocals 是具有层级的，可以被限定在以某个 composable 作为根结点的子树中，其默认会向下传递的，当然当前子树中的某个 composable 可以对该 CompositionLocals 进行覆盖，从而使得新值会在这个 composable 中继续向下传递",
                style = MaterialTheme.typography.body1)
            HorizontalDivide()
            Text(text = "CompositionLocal使用",style = MaterialTheme.typography.h6)
            //创建一个CompositionLocal 用于设置颜色
            val localTextColor = compositionLocalOf { Color.Green }
            CompositionLocalProvider(
                localTextColor provides Color.Blue
            ) {
                Text(text = "这里的颜色是蓝色的", color = localTextColor.current)

                CompositionLocalProvider(
                    localTextColor provides Color.Red
                ) {
                    Text(text = "这里的颜色是红色的", color = localTextColor.current)
                }

                Text(text = "这里的颜色是蓝色的", color = localTextColor.current)
            }

            Text(text = "这里的颜色是绿色的 默认初始值", color = localTextColor.current)


            HorizontalDivide()
            Text(text = "CompositionLocal和staticCompositionLocal的区别",style = MaterialTheme.typography.h6)
            Text(text = "staticCompositionLocal是一个静态的CompositionLocal 当它的值发生变化时 整个作用域都会触发重组 而CompositionLocal的值发生变化时 只会让依赖它的composable触发重组",
                style = MaterialTheme.typography.body1)


            var useStaticCompositionLocal  by remember {
                mutableStateOf(false)
            }
            CheckBoxText(text = "是否使用staticCompositionLocalOf", initChecked = useStaticCompositionLocal, onCheckedChange = {
                useStaticCompositionLocal = it
                boxColor = Color.Red
            })
            var staticTextColor by remember {
                mutableStateOf(Color.Green)
            }
//            val staticLocalBgColor by remember(useStaticCompositionLocal) {
//                derivedStateOf{
//                    if(useStaticCompositionLocal){
//                        staticCompositionLocalOf{ Color.Green }
//                    }else{
//                        compositionLocalOf{ Color.Green }
//                    }
//                }
//            }

            val staticLocalBgColor  = compositionLocalOf{ Color.Green }
            CompositionLocalProvider(
                staticLocalBgColor provides staticTextColor
            ){
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(boxColor),
                    contentAlignment = Alignment.Center){

                }
                extracted(staticLocalBgColor.current)
            }
            Button(onClick = {
                /**
                 * 如果整个作用域触发重组 那么Box的颜色就会变化
                 */
                staticTextColor = Color.Black
            }) {
                Text(text = "更新颜色")
            }

            boxColor = Color.Gray



        }
    }
}

@Composable
private fun extracted(
    textColor:Color,
) {
    Text(text = "text", color = textColor)
}