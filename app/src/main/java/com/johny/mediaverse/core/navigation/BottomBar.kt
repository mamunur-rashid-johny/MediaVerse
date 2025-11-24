package com.johny.mediaverse.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


val bottomNavItem = listOf(BottomNavItem.Movie, BottomNavItem.TvShow, BottomNavItem.Podcast, BottomNavItem.Bookmark)

@Composable
fun BottomBar(
    visibility: Boolean,
    navController: NavController
) {
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically(),
        content = {
            NavigationBar{
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRouteString = backStackEntry?.destination?.route

                bottomNavItem.forEach { des ->
                    val isSelected = currentRouteString == des.route::class.qualifiedName
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(des.route){
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = if (isSelected)  des.iconSelected else des.iconUnselected),
                                contentDescription = stringResource(des.title),
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        label = {
                            Text(text = stringResource(id = des.title))
                        }
                    )
                }
            }
        }
    )
}