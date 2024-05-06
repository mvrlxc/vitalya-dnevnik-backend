package com.example.schedule

import com.example.database.Schedule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureScheduleRouting() {
    routing {
        get("/schedule") {
            val response = ScheduleMethods.getSchedule("")
            call.respond(response)
        }

    }
}