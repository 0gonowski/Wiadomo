package pl.wiadomo.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Session(val token: String)

object SessionManager {
    private val path = File(System.getProperty("user.home"), ".wiadomo/session.json")
    private val json = Json { prettyPrint = true }

    fun saveSession(token: String) {
        path.parentFile.mkdirs()
        path.writeText(json.encodeToString(Session.serializer(), Session(token)))
    }

    fun loadSession(): String? {
        return if (path.exists()) {
            val session = json.decodeFromString(Session.serializer(), path.readText())
            session.token
        } else null
    }

    fun clearSession() {
        if (path.exists()) path.delete()
    }
}
