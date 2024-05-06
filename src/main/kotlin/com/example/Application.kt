package com.example

import com.example.auth.configureAuth
import com.example.database.DB
import com.example.database.configureDbRouting
import com.example.plugins.*
import com.example.schedule.configureScheduleRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}


fun Application.module() {
    configureSerialization()
    configureRouting()
    configureDbRouting()
    configureAuth()
    configureScheduleRouting()
    configureActive()
}

fun Application.configureActive(){
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            DB.connect()
            delay(15 * 60 * 1000)
        }
    }
}
