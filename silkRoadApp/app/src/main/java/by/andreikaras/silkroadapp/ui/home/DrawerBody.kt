package by.andreikaras.silkroadapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.R
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryDarkColor
import by.andreikaras.silkroadapp.ui.theme.categoryDividerColor
import by.andreikaras.silkroadapp.ui.theme.categoryNotTransparentBackgroundColor
import by.andreikaras.silkroadapp.ui.theme.defaultTextDarkColor

@Composable
fun DrawerBody(
    onCategoryClick: (String) -> Unit = {}
) {
    val categoriesList = listOf(
        stringResource(id = R.string.category_all),
        stringResource(id = R.string.category_self_defence),
        stringResource(id = R.string.category_medicine),
        stringResource(id = R.string.category_content),
        stringResource(id = R.string.category_second_hand)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(categoryNotTransparentBackgroundColor)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = stringResource(id = R.string.navigator_content_description),
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.navigator_list_title),
                color = AppPrimaryDarkColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(categoryDividerColor)
            )


            LazyColumn(Modifier.fillMaxSize()) {
                items(categoriesList) { item ->
                    Column(
                        modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCategoryClick(item)
                        },
                        verticalArrangement = Arrangement.Center)
                    {
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = item,
                            color = defaultTextDarkColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(1.dp)
                                .background(categoryDividerColor)
                        )
                    }
                }
            }
        }

    }
}