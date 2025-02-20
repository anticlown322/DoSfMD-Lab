package by.andreikaras.silkroadapp.ui.home.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun BottomMenu(
    selectedItem: String,
    onFavsClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Favourite,
        BottomMenuItem.Profile,
        BottomMenuItem.Settings
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item.route,
                onClick = {
                    when(item.route) {
                        BottomMenuItem.Home.route -> onHomeClick()
                        BottomMenuItem.Favourite.route -> onFavsClick()
                        BottomMenuItem.Profile.route -> onProfileClick()
                        BottomMenuItem.Settings.route -> onSettingsClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}