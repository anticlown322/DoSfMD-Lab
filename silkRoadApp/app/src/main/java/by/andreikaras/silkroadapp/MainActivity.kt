package by.andreikaras.silkroadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.andreikaras.silkroadapp.ui.details.DetailsScreenDataObject
import by.andreikaras.silkroadapp.ui.details.DetailsScreen
import by.andreikaras.silkroadapp.ui.home.HomeScreen
import by.andreikaras.silkroadapp.ui.login.LoginScreen
import by.andreikaras.silkroadapp.ui.home.HomeScreenDataObject
import by.andreikaras.silkroadapp.ui.login.LoginScreenDataObject
import by.andreikaras.silkroadapp.ui.profile.ProfileScreen
import by.andreikaras.silkroadapp.ui.profile.ProfileScreenDataObject
import by.andreikaras.silkroadapp.ui.theme.SilkRoadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SilkRoadAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LoginScreenDataObject
                ) {
                    composable<LoginScreenDataObject> {
                        LoginScreen { navData ->
                            navController.navigate(navData)
                        }
                    }

                    composable<HomeScreenDataObject> { navEntry ->
                        val navData = navEntry.toRoute<HomeScreenDataObject>()
                        HomeScreen(
                            navData,
                            onGoodClick = { uid, good ->
                                navController.navigate(
                                    DetailsScreenDataObject(
                                        uid = uid,
                                        category = good.category,
                                        name = good.name,
                                        description = good.description,
                                        price = good.price,
                                        mainImage = good.mainImage,
                                        sliderImages = good.sliderImages,
                                        isFavourite = good.isFavourite
                                    )
                                )
                            },
                            onProfileClick = { user ->
                                navController.navigate(
                                    ProfileScreenDataObject(
                                        uid = user.accId,
                                        email = user.email,
                                        registrationDate = user.registrationDate,
                                        name = user.name,
                                        phone = user.phone,
                                        surname = user.surname,
                                        birthDate = user.birthDate,
                                        country = user.country,
                                        status = user.status,
                                        sex = user.sex,
                                        city = user.city
                                    )
                                )
                            },
                            onSettingsClick = {}
                        )
                    }

                    composable<DetailsScreenDataObject> { navEntry ->
                        val navData = navEntry.toRoute<DetailsScreenDataObject>()
                        DetailsScreen(navData)
                    }

                    composable<ProfileScreenDataObject> { navEntry ->
                        val navData = navEntry.toRoute<ProfileScreenDataObject>()
                        ProfileScreen(
                            navData,
                            onDeleteOrSignOut = {
                                navController.navigate(LoginScreenDataObject)
                            }
                        )
                    }
                }
            }
        }
    }
}