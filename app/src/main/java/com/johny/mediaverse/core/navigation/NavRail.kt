package com.johny.mediaverse.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val railNavItem =
    listOf(BottomNavItem.Movie, BottomNavItem.TvShow, BottomNavItem.Podcast, BottomNavItem.Bookmark)

@Composable
fun NavRail(
    visibility: Boolean,
    navController: NavController
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally()
    ) {
        NavigationRail(
            modifier = Modifier
                .shadow(elevation = 6.dp, spotColor = Color.Black, ambientColor = Color.Black)
                .zIndex(1f),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRouteString = backStackEntry?.destination?.route
            railNavItem.forEach { des ->
                val isSelected = currentRouteString == des.route::class.qualifiedName
                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(des.route) {
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
                            painter = painterResource(id = if (isSelected) des.iconSelected else des.iconUnselected),
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

}