import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pl.wiadomo.config.SessionManager
import pl.wiadomo.ui.LoginScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Wiadomo - Logowanie") {
        MaterialTheme {
            val tokenState = remember { mutableStateOf(SessionManager.loadSession()) }
            val token = tokenState.value

            if (token == null) {
                LoginScreen(onSuccess = {
                    tokenState.value = it
                    SessionManager.saveSession(it)
                })
            } else {
                Text("✅ Zalogowano! Token: ${token.take(20)}...")
                // TODO: przejście do głównego ekranu z modemem
            }
        }
    }
}
