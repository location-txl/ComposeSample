package com.location.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.location.compose.sample.bottom.HomeScreen
import com.location.compose.sample.ui.theme.ComposeSampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val homeList by lazy {
        listOf(HomeScreen.Weight, HomeScreen.Layout, HomeScreen.Anim, HomeScreen.Modifier)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch{
            enableEdgeToEdge()
        }
        setContent {
            ComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }

    @Composable
    private fun Home() {
        //控制器
        val navController = rememberNavController()
        Scaffold(bottomBar = {
            //添加底部导航栏
            BottomBar(navController)
        }) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen.Weight.rotateName,
                modifier = Modifier.padding(it)
            ) {
                // dsa

                weightGraph(
                    navigateRotate = { route ->
                        navController.navigate(route = route)
                    },
                    back = {
                        navController.popBackStack()
                    }
                )

                layoutGraph(
                    navigateRotate = {
                        route ->
                        navController.navigate(route)
                    },
                    back = {
                        navController.popBackStack()
                    }
                )

                animGraph(
                    navigateRotate = {
                        route ->
                        navController.navigate(route)
                    },
                    back = {
                        navController.popBackStack()
                    }
                )
                otherGraph(
                    navigateRotate = {
                        route ->
                        navController.navigate(route)
                    },
                    back = {
                        navController.popBackStack()
                    }
                )

            }
        }
    }


    @Composable
    private fun BottomBar(nav: NavHostController) {
        BottomAppBar {
            val navBackStackEntry by nav.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            homeList.forEach {
                val checked =
                    currentDestination?.hierarchy?.any { any -> any.route == it.rotateName } == true

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    },
                    selected = checked,
                    label = {
                        //不同的颜色
                        Text(
                            stringResource(it.titleId),
                            color = if (checked) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = {
                        nav.navigate(route = it.rotateName) {
                            popUpTo(nav.graph.findStartDestination().id) {
                                saveState = true
                            }
                            //防止在顶部是多次创建
                            launchSingleTop = true
                            // 恢复状态
                            restoreState = true
                        }
                    })
            }
        }

//        BottomAppBar {
//
//        }
    }
}

