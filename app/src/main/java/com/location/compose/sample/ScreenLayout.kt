package com.location.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.location.compose.sample.bottom.HomeScreen
import com.location.compose.sample.bottom.LayoutScreen

/**
 *
 * @author tianxiaolong
 * time：2022/9/1 18:44
 * description：
 */
fun NavGraphBuilder.layoutGraph(navigateRotate: (String) -> Unit, back: () -> Unit) {
    navigation(
        route = HomeScreen.Layout.rotateName,
        startDestination = LayoutScreen.START
    ) {
        composable(route = LayoutScreen.START) {
            LayoutHome(navigateRotate)
        }
        for (layoutItem in LayoutScreen.LayoutItems) {
            composable(route = layoutItem.routeName) {
                layoutItem.content(back, navigateRotate)
            }
            fun composeChildScreen(screen: LayoutScreen) {
                screen.subScreen?.let {
                    for (childScreen in it) {
                        composable(route = childScreen.routeName) {
                            childScreen.content(back, navigateRotate)
                        }
                        composeChildScreen(childScreen)
                    }
                }
            }
            composeChildScreen(layoutItem)
        }
    }
}


@Composable
inline fun LayoutHome(crossinline navigateRotate: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        content = {
            itemsIndexed(LayoutScreen.LayoutItems) { _, item ->
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    OutlinedButton(
                        onClick = {
                            navigateRotate(item.routeName)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(text = item.name)
                    }
                }
            }
        })
}


