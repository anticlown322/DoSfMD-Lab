package by.andreikaras.silkroadapp.ui.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeScreenDataObject(
    val uid: String = "",
    val email: String = ""
)
