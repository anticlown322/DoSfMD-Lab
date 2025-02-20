package by.andreikaras.silkroadapp.ui.details

import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreenDataObject(
    val uid: String = "",
    val key: String = "",
    val category: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val mainImage: String = "",
    val sliderImages: List<String> = emptyList(),
    val isFavourite: Boolean = false
)
