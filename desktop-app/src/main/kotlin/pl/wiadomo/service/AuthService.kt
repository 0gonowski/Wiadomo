import io.github.jan.supabase.gotrue.providers.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.wiadomo.service.SupabaseClientProvider

object AuthService {

    fun login(email: String, password: String, onResult: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                SupabaseClientProvider.client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                val token = SupabaseClientProvider.client.auth.currentSessionOrNull()?.accessToken
                println("✅ Zalogowano jako: ${SupabaseClientProvider.client.auth.currentUserOrNull()?.email}")
                onResult(token)
            } catch (e: Exception) {
                println("❌ Błąd logowania: ${e.message}")
                onResult(null)
            }
        }
    }
}
