package by.andreikaras.silkroadapp.ui.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileScreenDataObject(
    val uid: String = "",
    val email: String = "",
    val registrationDate: String = "",
    val name: String = "",
    val phone: String = "",
    val surname: String = "",
    val birthDate: String = "",
    val country: String = "",
    val status: String = "",
    val sex: String = "",
    val city: String = ""
)
