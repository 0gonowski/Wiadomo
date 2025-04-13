package pl.wiadomo.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import pl.wiadomo.config.Env

object SupabaseClientProvider {
    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = Env.supabaseUrl,
            supabaseKey = Env.supabaseApiKey
        ) {
            install(Auth)
        }
    }
}
