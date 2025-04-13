plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("org.jetbrains.compose") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
    application
}

group = "pl.wiadomo"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.github.com/supabase-community/supabase-kt") {
        credentials {
            username = project.findProperty("gpr.user") as String
            password = project.findProperty("gpr.key") as String
        }
    }
}

dependencies {
    implementation(compose.desktop.currentOs)

    // Supabase
    implementation("io.github.jan-tennert.supabase:main-kt:3.1.4")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.1.4")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.1.4")

    // Inne
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("com.fazecast:jSerialComm:2.9.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.1.4")


    // Kotlin stdlib (opcjonalnie przy Kotlin 2.0+)
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("pl.wiadomo.MainKt")
}

kotlin {
    jvmToolchain(17)
}
