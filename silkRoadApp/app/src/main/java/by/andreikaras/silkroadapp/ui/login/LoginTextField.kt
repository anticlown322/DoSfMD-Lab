package by.andreikaras.silkroadapp.ui.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryDarkColor
import by.andreikaras.silkroadapp.ui.theme.focusedTextColor
import by.andreikaras.silkroadapp.ui.theme.unfocusedTextColor

@Composable
fun RoundedCornerTextField(
    input: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = input,
        onValueChange = {
            onValueChange(it)
        },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 50.dp)
            .border(
                1.dp,
                AppPrimaryDarkColor,
                RoundedCornerShape(25.dp)),
        label = {
            Text(
                text = label,
                color = AppPrimaryDarkColor
            ) },
        singleLine = true
    )
}