package com.example.database

import com.example.Admin
import com.example.models.Text
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureDbRouting() {
    routing {

        post("/connect") {
            val pass = call.receive(Text::class)
            if (pass.text == Admin.PASS) {
                try {

                    DB.connect()
                    call.respond(Text("success"))
                } catch (e: Exception) {
                    call.respond(Text(e.toString()))
                }
            } else {
                call.respond(Text("invalid password"))
            }
        }

        post("/tables/create") {
            val pass = call.receive(Text::class)
            if (pass.text == Admin.PASS) {
                try {
                    DB.createTables()
                    call.respond(Text("success"))
                } catch (e: Exception) {
                    call.respond(Text(e.toString()))
                }
            } else {
                call.respond(Text("invalid password"))
            }
        }

        get("/test") {
            val a = DAO.insert()
            call.respond(a)
        }

        get("/negr"){
            val a = DAO.getAllDates()
            call.respond(a)
        }

    }
}
