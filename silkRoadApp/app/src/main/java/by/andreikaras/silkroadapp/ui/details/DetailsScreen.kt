package by.andreikaras.silkroadapp.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.entities.GoodModel
import by.andreikaras.silkroadapp.ui.theme.itemPriceColor
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun DetailsScreen(
    navObject: DetailsScreenDataObject = DetailsScreenDataObject()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    )
    {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = navObject.mainImage,
                    contentDescription = "Poster image of the product",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .weight(1.2f)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.FillHeight
                )

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .weight(0.8f)
                ) {
                    Text(
                        text = "Category",
                        fontSize = 25.sp,
                        textDecoration = TextDecoration.Underline
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = navObject.category,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Price",
                        fontSize = 25.sp,
                        textDecoration = TextDecoration.Underline
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "${navObject.price} $",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = itemPriceColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = navObject.name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = navObject.description,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            ImageSlider(images = navObject.sliderImages)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {

            }) {
                Text(text = "Buy for ${navObject.price} $")
            }
        }
    }
}