package pl.wiadomo.config

import io.github.cdimascio.dotenv.dotenv

object Env {
    private val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val supabaseUrl = dotenv["SUPABASE_URL"] ?: error("Brak SUPABASE_URL w .env")
    val supabaseApiKey = dotenv["SUPABASE_API_KEY"] ?: error("Brak SUPABASE_API_KEY w .env")
}
