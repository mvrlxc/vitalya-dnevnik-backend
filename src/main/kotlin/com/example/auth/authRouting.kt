package com.example.auth

import ch.qos.logback.core.subst.Token
import com.example.database.DAO
import com.example.models.Text
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureAuth() {

    routing {

        post("/auth/login") {
            val request = call.receive(AuthModel::class)
            val response = Auth.login(request.login, request.password)
            if (response == "invalid login") {
                call.respond(HttpStatusCode(402, "invalid login"))
            } else if (response == "invalid password") {
                call.respond(HttpStatusCode(403, "invalid password"))
            } else
                call.respond(Text(response))
        }

        post("/auth/register") {
            val request = call.receive(AuthModel::class)
            val response = Auth.register(request.login, request.password, request.username)
            if (response == "login already exists") {
                call.respond(HttpStatusCode(403, "login already exists"))
            } else
                call.respond(Text(response))

        }

        post("/getUsername") {
            val token = call.receive(GetUsername::class)
            val response = DAO.getUsername(token.token)
            call.respond(Text(response))
        }

    }
}

@Serializable
data class AuthModel(
    val username: String,
    val login: String,
    val password: String,

    )

@Serializable
data class GetUsername(
    val token: String
)