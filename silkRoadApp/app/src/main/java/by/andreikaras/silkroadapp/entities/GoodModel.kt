package by.andreikaras.silkroadapp.entities

import kotlinx.serialization.Serializable

@Serializable
data class GoodModel(
    val key: String = "",
    val category: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val mainImage: String = "",
    val isFavourite : Boolean = false,
    val sliderImages: List<String> = emptyList()
)
