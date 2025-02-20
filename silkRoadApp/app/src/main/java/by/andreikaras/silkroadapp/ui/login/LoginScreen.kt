package by.andreikaras.silkroadapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.andreikaras.silkroadapp.R
import by.andreikaras.silkroadapp.entities.UserModel
import by.andreikaras.silkroadapp.ui.home.HomeScreenDataObject
import by.andreikaras.silkroadapp.ui.theme.AppBackgroundColor
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryDarkColor
import by.andreikaras.silkroadapp.ui.theme.AppPrimaryLightColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun LoginScreen(
    onNavigationToHomeScreen: (HomeScreenDataObject) -> Unit
) {
    val auth = remember { Firebase.auth }

    val errorState = remember {
        mutableStateOf("")
    }

    val emailState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
    )



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = stringResource(id = R.string.login_content_description),
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            color = AppPrimaryLightColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = stringResource(id = R.string.short_app_description),
            color = AppPrimaryDarkColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            input = emailState.value,
            label = stringResource(id = R.string.email_field_title)
        ) {
            emailState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            input = passwordState.value,
            label = stringResource(id = R.string.password_field_title)
        ) {
            passwordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        LoginButton(label = stringResource(id = R.string.sing_in_button)) {
            signIn(
                auth,
                emailState.value,
                passwordState.value,
                onSignInSuccess = { navData -> onNavigationToHomeScreen(navData) },
                onSignInFailure = { error -> errorState.value = error }
            )
        }

        LoginButton(label = stringResource(id = R.string.sing_up_button)) {
            signUp(
                auth,
                emailState.value,
                passwordState.value,
                onSignUpSuccess = { navData -> onNavigationToHomeScreen(navData) },
                onSignUpFailure = { error -> errorState.value = error }
            )
        }
    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (HomeScreenDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if (email.isBlank()) {
        onSignUpFailure("Email can not be empty!")
        return
    }

    if (password.isBlank()) {
        onSignUpFailure("Password can not be empty!")
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val date = Calendar.getInstance().time
                val user = UserModel(
                    name = "",
                    email = email,
                    registrationDate = date.toString("dd.MM.yyyy"),
                    surname = "",
                    country = "",
                    city = "",
                    birthDate = "",
                    sex = "",
                    phone = "",
                    status = ""
                )

                val firestore = Firebase.firestore
                firestore.collection("users")
                    .document(auth.uid.toString())
                    .set(user)

                onSignUpSuccess(
                    HomeScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign up error!")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (HomeScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {
    if (email.isBlank()) {
        onSignInFailure("Email can not be empty!")
        return
    }

    if (password.isBlank()) {
        onSignInFailure("Password can not be empty!")
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                onSignInSuccess(
                    HomeScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign in error!")
        }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

