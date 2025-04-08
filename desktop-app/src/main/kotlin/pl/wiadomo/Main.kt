package pl.wiadomo

import com.fazecast.jSerialComm.SerialPort

fun main() {
    println("🔍 Rozpoczynam automatyczne skanowanie portów...")

    val ports = SerialPort.getCommPorts()
    var znalezionoModem = false

    for (port in ports) {
        println("🔄 Sprawdzam port ${port.systemPortName} - ${port.descriptivePortName}")

        port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY)
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 1000)

        if (!port.openPort()) {
            println("❌ Nie udało się otworzyć portu ${port.systemPortName}")
            continue
        }

        try {
            val out = port.outputStream
            val input = port.inputStream

            fun sendCommand(cmd: String): String {
                out.write("$cmd\r".toByteArray())
                out.flush()
                Thread.sleep(500)
                val response = StringBuilder()
                while (input.available() > 0) {
                    response.append(input.read().toChar())
                }
                return response.toString().trim()
            }

            // 1. Test AT
            val at = sendCommand("AT")
            if (!at.contains("OK", ignoreCase = true)) {
                println("❌ Brak odpowiedzi na AT. Pomijam port.")
                continue
            }

            // 2. Test CMGF (tryb SMS)
            val cmgf = sendCommand("AT+CMGF=1")
            if (!cmgf.contains("OK", ignoreCase = true)) {
                println("⛔ Port nie obsługuje SMS (AT+CMGF=1 → ERROR).")
                continue
            }

            // Sukces 🎉
            println("✅ ZNALEZIONO MODEM SMS na porcie ${port.systemPortName}")
            znalezionoModem = true
            // możesz tu zapisać port/systemPortName do użycia dalej
            break

        } catch (e: Exception) {
            println("⚠️ Błąd komunikacji z portem: ${e.message}")
        } finally {
            port.closePort()
        }
    }

    if (!znalezionoModem) {
        println("""
❌ Nie znaleziono żadnego modemu SMS.

🛠 Upewnij się, że:
- Modem jest podłączony przez USB
- Nie działa w trybie Wi-Fi lub CD-ROM
- Masz zainstalowane sterowniki (np. Huawei Mobile Partner)

""".trimIndent())
    }
}
