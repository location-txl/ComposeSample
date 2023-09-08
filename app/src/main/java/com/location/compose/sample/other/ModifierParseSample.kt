package com.location.compose.sample.modifier

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableValue
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.location.compose.sample.common.HorizontalDivide
import com.location.compose.sample.common.LocalHorDivideStyle
import com.location.compose.sample.common.TitleBar
import com.location.compose.sample.common.VerticalDivide
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

val innerMethod = CombinedModifier::class.declaredMemberProperties.find {
    it.name == "inner"
}!!.apply {
    isAccessible = true
}
val outerMethod = CombinedModifier::class.declaredMemberProperties.find {
    it.name == "outer"
}!!.apply {
    isAccessible = true
}


private val boxSize = DpSize(150.dp, 120.dp)

@Composable
fun ScreenModifierParseSample(back: () -> Unit) {
    TitleBar(title = "解析Modifier", back = back) {
        Column {
            Text(
                text = "点击添加Modifier 添加Modifier 可以看到对应的Modifier结构",
                style = MaterialTheme.typography.body1
            )
            var parseModifier: Modifier by remember {
                mutableStateOf(Modifier)
            }
            var showSelectDialog by remember {
                mutableStateOf(false)
            }
            Button(
                onClick = {
                    showSelectDialog = true
                }) {
                Text(text = "添加Modifier")
            }
            LazyColumn {
                var tempModifier = parseModifier
                while (tempModifier != Modifier) {
                    val realModifier = tempModifier
                    val (modifier, isComb) = when (tempModifier) {
                        is CombinedModifier -> {
                            val inner = innerMethod.get(tempModifier) as Modifier
                            val outer = outerMethod.get(tempModifier) as Modifier
                            tempModifier = outer
                            inner to true
                        }
                        else -> {
                            tempModifier.also {
                                tempModifier = Modifier
                            } to false
                        }
                    }

                    item {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                if (isComb) {
                                    Box(
                                        modifier = Modifier
                                            .size(boxSize)
                                            .background(Color(0xFFDEB762)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "CombinedModifier",
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                    HorizontalDivide(modifier = Modifier.width(20.dp))
                                    Text(text = "inner")
                                    HorizontalDivide(modifier = Modifier.width(20.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .size(boxSize)
                                        .background(Color(0xFFA288DF))
                                        .clickable {
                                            parseModifier = realModifier
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = modifier.name,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                            if(isComb){
                                VerticalDivide(modifier = Modifier
                                    .padding(start = boxSize.width / 2 - LocalHorDivideStyle.current.size - LocalHorDivideStyle.current.padding)
                                    .height(20.dp))
                                Text(text = "outer",
                                modifier = Modifier.padding(start =  boxSize.width / 2 - 23.dp))
                                VerticalDivide(modifier = Modifier
                                    .padding(start = boxSize.width / 2 - LocalHorDivideStyle.current.size - LocalHorDivideStyle.current.padding)
                                    .height(20.dp))
                            }
                        }
                    }
                }
            }
            if (showSelectDialog) {
                isDebugInspectorInfoEnabled = true
                ShowModifierSelectDialog(
                    modifierParse = parseModifier
                ) {
                    parseModifier = it
                    showSelectDialog = false
                    isDebugInspectorInfoEnabled = false
                }
            }
        }
    }

}


@Composable
fun ShowModifierSelectDialog(
    @SuppressLint("ModifierParameter") modifierParse: Modifier,
    block: (Modifier) -> Unit
) {
    Dialog(onDismissRequest = { }) {
        val modifierList = remember(key1 = Unit) {
            listOf(
                ModifierItem("size") { Modifier.size(200.dp) },
                ModifierItem("padding") { Modifier.padding(20.dp) },
                ModifierItem("background") { Modifier.background(Color.Red) },
                ModifierItem("fillMaxHeight") { Modifier.fillMaxHeight() },
                ModifierItem("fillMaxWidth") { Modifier.fillMaxWidth() },
                ModifierItem("fillMaxSize") { Modifier.fillMaxSize() },
                ModifierItem("clickable") { Modifier.clickable { } },
            )
        }
        var uiModifier: Modifier by remember(modifierParse) {
            mutableStateOf(modifierParse)
        }
        var text by remember {
            mutableStateOf(parseModifier(modifierParse))
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
            Text(text = "点击添加Modifier", style = MaterialTheme.typography.h5)
            LazyVerticalGrid(columns = GridCells.Fixed(3)){
                items(
                    modifierList,
                ) {
                    Text(text = it.name,
                        modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            uiModifier = uiModifier.then(it.modifierCreate())
                            text = parseModifier(uiModifier)
                        }
                        .padding(10.dp)
                    )
                }
            }
            Button(onClick = {
                block(uiModifier)
            }) {
                Text(text = "确定")
            }
        }
    }
}

private fun parseModifier(modifier: Modifier): String {
    return modifier.foldIn("Modifier") { str, m ->
        if (m is InspectableValue) {
            str + "\n        .${m.nameFallback}()"
        } else {
            str + m.toString()
        }
    }
}

private val Modifier.name: String
    get() {
        return when (this) {
            is InspectableValue -> {
                nameFallback!!
            }
            else -> {
                toString()
            }
        }
    }


data class ModifierItem(val name: String, val modifierCreate: () -> Modifier) {
}

@Preview
@Composable
fun ScreenModifierParseSamplePreview() {
    ScreenModifierParseSample {

    }
}