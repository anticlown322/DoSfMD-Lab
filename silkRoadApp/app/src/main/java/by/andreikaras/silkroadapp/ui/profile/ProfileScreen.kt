package by.andreikaras.silkroadapp.ui.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import by.andreikaras.silkroadapp.entities.UserModel
import by.andreikaras.silkroadapp.ui.theme.categoryDividerColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileScreen(
    navObject: ProfileScreenDataObject = ProfileScreenDataObject(),
    onDeleteOrSignOut: () -> Unit
) {
    if (navObject.uid.isBlank() || navObject.email.isBlank()) {
        return
    }

    val auth = remember { Firebase.auth }
    val firestore = remember { Firebase.firestore }

    val userId = navObject.uid
    var userProfile by remember { mutableStateOf<UserModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { user ->
                userProfile = user.toObject(UserModel::class.java)

                if (userProfile == null) {
                    Log.d("MyLog", "Loaded profile is null")
                } else {
                    userProfile!!.accId = navObject.uid

                    Log.d("MyLog", "Successful profile loading")
                }

                isLoading = false
            }
            .addOnFailureListener { exception ->
                Log.d("MyLog", "Error while loading profile: ${exception.message}")
                isLoading = false
            }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (userProfile == null) {
            Text("Profile not found")
            return
        }

        userProfile?.let { profile ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    EditableProfileField("Name", profile.name) { newValue ->
                        userProfile = userProfile?.copy(name = newValue)
                        updateField(firestore, userId, "name", newValue)
                    }

                    EditableProfileField("Surname", profile.surname) { newValue ->
                        userProfile = userProfile?.copy(surname = newValue)
                        updateField(firestore, userId, "surname", newValue)
                    }

                    EditableProfileField("Country", profile.country) { newValue ->
                        userProfile = userProfile?.copy(country = newValue)
                        updateField(firestore, userId, "country", newValue)
                    }

                    EditableProfileField("City", profile.city) { newValue ->
                        userProfile = userProfile?.copy(city = newValue)
                        updateField(firestore, userId, "city", newValue)
                    }

                    EditableProfileField("Birth date", profile.birthDate) { newValue ->
                        userProfile = userProfile?.copy(birthDate = newValue)
                        updateField(firestore, userId, "birthDate", newValue)
                    }

                    EditableProfileField("Sex", profile.sex) { newValue ->
                        userProfile = userProfile?.copy(sex = newValue)
                        updateField(firestore, userId, "sex", newValue)
                    }

                    EditableProfileField("Phone number", profile.phone) { newValue ->
                        userProfile = userProfile?.copy(phone = newValue)
                        updateField(firestore, userId, "phone", newValue)
                    }

                    EditableProfileField("Status", profile.status) { newValue ->
                        userProfile = userProfile?.copy(status = newValue)
                        updateField(firestore, userId, "status", newValue)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(1.dp)
                            .background(categoryDividerColor)
                    )

                    ProfileField("Email", profile.email)

                    ProfileField("Registration date", profile.registrationDate)

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(1.dp)
                            .background(categoryDividerColor)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            auth.signOut()
                            onDeleteOrSignOut()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("Sign out")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Delete account", color = Color.White)
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete account") },
            text = { Text("Are you sure you want to delete your account? This action is irreversible.") },
            confirmButton = {
                Button(
                    onClick = {
                        deleteAccount(auth, firestore, userId) { success ->
                            if (success) {
                                showDeleteDialog = false
                                onDeleteOrSignOut()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDeleteDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun updateField(
    firestore: FirebaseFirestore,
    userId: String,
    fieldName: String,
    value: String
) {
    firestore.collection("users").document(userId)
        .update(fieldName, value)
        .addOnSuccessListener {
            Log.d("MyLog", "$fieldName updated successfully")
        }
        .addOnFailureListener {
            Log.d("MyLog", "Error while updating $fieldName: ${it.message}")
        }
}

private fun deleteAccount(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore,
    userId: String,
    onResult: (Boolean) -> Unit
) {
    val currentUser = auth.currentUser
    if (currentUser != null) {
        firestore.collection("users")
            .document(userId)
            .delete()
            .addOnSuccessListener {
                Log.d("MyLog", "Account deletion process started")
                currentUser.delete()
                    .addOnCompleteListener { task ->
                        Log.d("MyLog", "Account deletion process finished]")
                        onResult(task.isSuccessful)
                    }
            }
            .addOnFailureListener {
                Log.d("MyLog", "Account deletion can't be started: ${it.message}")
                onResult(false)
            }
    } else {
        onResult(false)
    }
}