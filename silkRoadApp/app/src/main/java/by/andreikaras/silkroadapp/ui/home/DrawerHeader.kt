package by.andreikaras.silkroadapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.R
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryLightColor
import by.andreikaras.silkroadapp.ui.theme.categoryNotTransparentBackgroundColor

@Composable
fun DrawerHeader() {
    Column (
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(categoryNotTransparentBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = AppPrimaryLightColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
    }
}