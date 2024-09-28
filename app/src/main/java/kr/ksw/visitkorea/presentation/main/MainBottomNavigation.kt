package kr.ksw.visitkorea.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun MainBottomNavigation(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: MainRoute = navBackStackEntry?.destination?.route?.let {
        MainRoute.entries.find { route -> it == route.route }
    } ?: MainRoute.HOME

    MainBottomNavigation(
        currentRoute = currentRoute
    ) { newRoute ->
        navController.navigate(route = newRoute.route) {
            navController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            this.launchSingleTop = true
            this.restoreState = true
        }
    }
}

@Composable
fun MainBottomNavigation(
    currentRoute: MainRoute,
    onTabClick: (MainRoute) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MainRoute.entries.forEach {
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(vertical = 4.dp)
                    .clickable {
                        onTabClick(it)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(it.icon),
                    contentDescription = stringResource(it.title),
                    tint = if(currentRoute == it) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.LightGray
                    }
                )
                Text(
                    text = stringResource(it.title),
                    fontSize = 12.sp,
                    color = if(currentRoute == it) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.LightGray
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainBottomNavigationPreview() {
    VisitKoreaTheme {
        Surface {
            var currentRoute by remember {
                mutableStateOf(MainRoute.HOME)
            }
            MainBottomNavigation(
                currentRoute = currentRoute
            ) { route ->
                currentRoute = route
            }
        }
    }
}