package com.location.compose.sample.scope

import androidx.compose.runtime.*

fun radioGroupScopeOf(initSelectIndex: Int = 0, selectChange: (Int) -> Unit): RadioGroupScope =
    RadioGroupScopeImpl(initSelectIndex, selectChange)

/**
 *
 * @author tianxiaolong
 * time：2022/8/17 16:48
 * description：
 */
internal class RadioGroupScopeImpl(initSelectIndex: Int, val selectChange: (Int) -> Unit) :
    RadioGroupScope {

    private var selectIndex by mutableStateOf(initSelectIndex);
    var index = 0;

    @Composable
    override fun Item(content: @Composable (checked: Boolean, onClick: () -> Unit) -> Unit) {

        val curIndex: Int = remember {
            index++
        }
        content(
            curIndex == selectIndex
        ) {
            selectIndex = curIndex
            selectChange(curIndex)
        }
    }
}