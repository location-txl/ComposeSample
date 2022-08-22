package com.location.compose.sample.scope

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *
 * @author tianxiaolong
 * time：2022/8/17 16:40
 * description：
 */
interface RadioGroupScope {
    @Composable
    fun Item(content: @Composable (checked: Boolean, onClick: () -> Unit) -> Unit)
}