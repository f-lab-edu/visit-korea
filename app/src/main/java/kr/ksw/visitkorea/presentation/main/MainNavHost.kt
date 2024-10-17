package kr.ksw.visitkorea.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kr.ksw.visitkorea.presentation.festival.screen.FestivalScreen
import kr.ksw.visitkorea.presentation.home.screen.HomeScreen
import kr.ksw.visitkorea.presentation.hotel.screen.HotelScreen
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute.HOME.route
    ) {
        composable(route = MainRoute.HOME.route) {
            HomeScreen()
        }
        composable(route = MainRoute.HOTEL.route) {
            HotelScreen()
        }
        composable(route = MainRoute.EVENT.route) {
            FestivalScreen()
        }
        composable(route = MainRoute.SEARCH.route) {
            SampleScreen {
                Text(
                    text = "SEARCH",
                    fontSize = 32.sp
                )
            }
        }
        composable(route = MainRoute.FAVORITE.route) {
            SampleScreen {
                Text(
                    text = "FAVORITE",
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Composable
fun SampleScreen(
    text: @Composable () -> Unit
) {
    VisitKoreaTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                text()
            }
        }
    }
}