package by.andreikaras.silkroadapp.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryDarkColor
import by.andreikaras.silkroadapp.ui.theme.defaultTextDarkColor

@Composable
fun EditableProfileField(label: String, value: String?, onValueChange: (String) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var editableValue by remember { mutableStateOf(value ?: "") }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppPrimaryDarkColor
        )

        if (isEditing) {
            OutlinedTextField(
                value = editableValue,
                onValueChange = { editableValue = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        onValueChange(editableValue)
                        isEditing = false
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save changes")
                    }
                }
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (value.isNullOrEmpty()) "No infо" else value,
                    fontSize = 16.sp,
                    color = defaultTextDarkColor,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isEditing = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}