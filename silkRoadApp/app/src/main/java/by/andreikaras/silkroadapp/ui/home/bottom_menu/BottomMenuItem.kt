package by.andreikaras.silkroadapp.ui.home.bottom_menu

import by.andreikaras.silkroadapp.R

sealed class BottomMenuItem(
    val route : String,
    val title : String,
    val iconId : Int
) {
    data object Home : BottomMenuItem(
        route = "home",
        title = "Home",
        iconId = R.drawable.ic_home
    )

    data object Profile : BottomMenuItem(
        route = "profile",
        title = "Profile",
        iconId = R.drawable.ic_profile
    )

    data object Favourite : BottomMenuItem(
        route = "favs",
        title = "Favs",
        iconId = R.drawable.ic_favs
    )

    data object Settings : BottomMenuItem(
        route = "settings",
        title = "Settings",
        iconId = R.drawable.ic_settings
    )
}