package com.location.compose.sample



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.location.compose.sample.bottom.ScreenModifier
import com.location.compose.sample.bottom.HomeScreen

/**
 *
 * @author tianxiaolong
 * time：2022/9/1 18:44
 * description：
 */
fun NavGraphBuilder.modifierGraph(navigateRotate: (String) -> Unit, back: () -> Unit) {
    navigation(
        route = HomeScreen.Modifier.rotateName,
        startDestination = ScreenModifier.START
    ) {
        composable(route = ScreenModifier.START) {
            ModifierHome(navigateRotate)
        }
        for (animItem in ScreenModifier.ModifierItems) {
            composable(route = animItem.routeName) {
                animItem.content(animItem.subScreen, back, navigateRotate)
            }
            fun composeChildScreen(screen: ScreenModifier) {
                screen.subScreen?.let {
                    for (childScreen in it) {
                        composable(route = childScreen.routeName) {
                            childScreen.content(childScreen.subScreen, back, navigateRotate)
                        }
                        composeChildScreen(childScreen)
                    }
                }
            }
            composeChildScreen(animItem)
        }
    }
}


@Composable
inline fun ModifierHome(crossinline navigateRotate: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        content = {
            itemsIndexed(ScreenModifier.ModifierItems) { index, item ->
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


