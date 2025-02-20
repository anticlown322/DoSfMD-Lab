package by.andreikaras.silkroadapp.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryDarkColor
import by.andreikaras.silkroadapp.ui.theme.defaultTextDarkColor

@Composable
fun ProfileField(
    label: String,
    value: String?)
{
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppPrimaryDarkColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = if (value.isNullOrEmpty()) "No info" else value,
            fontSize = 16.sp,
            color = defaultTextDarkColor
        )
    }
}