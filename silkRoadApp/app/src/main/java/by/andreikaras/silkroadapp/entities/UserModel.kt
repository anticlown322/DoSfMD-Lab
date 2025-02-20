package by.andreikaras.silkroadapp.entities

data class UserModel(
    var accId: String = "",
    val name: String = "",
    val email: String = "",
    val registrationDate: String = "",
    val phone: String = "",
    val surname: String = "",
    val birthDate: String = "",
    val country: String = "",
    val status: String = "",
    val sex: String = "",
    val city: String = "",
    val favs: List<String> = emptyList()
)

