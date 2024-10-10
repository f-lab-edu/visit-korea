package kr.ksw.visitkorea.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.ksw.visitkorea.R

enum class MainRoute(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    HOME(route = "home", icon = R.drawable.baseline_home_24, title = R.string.main_tab_home_title),
    HOTEL(route = "hotel", icon = R.drawable.baseline_hotel_24, title = R.string.main_tab_hotel_title),
    EVENT(route = "event", icon = R.drawable.baseline_event_24, title = R.string.main_tab_event_title),
    SEARCH(route = "search", icon = R.drawable.baseline_search_24, title = R.string.main_tab_search_title),
    FAVORITE(route = "favorite", icon = R.drawable.baseline_favorite_24, title = R.string.main_tab_favorite_title),
}