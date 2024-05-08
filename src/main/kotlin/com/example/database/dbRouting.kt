package com.example.database

import com.example.Admin
import com.example.models.Text
import com.example.schedule.CommentsList
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

        get("/negr") {
            val a = DAO.getAllDates()
            call.respond(a)
        }

        post("/getComments") {
            val id = call.receive(Text::class)
            try {


                val a = DAO.getComments(id.text.toInt())
                call.respond(a)
            } catch (e: Exception) {
                call.respond(Text("$e"))
            }
        }

        post("/createComment") {
            val comment = call.receive<CommentsListADD>()
            try {
                DAO.addComment(
                    token = comment.token,
                    lessonIDX = comment.lessonID,
                    contentX = comment.content,
                    sendingDateTimeX = comment.sendingDateTime
                )
                call.respond(Text("success"))
            } catch (e: Exception) {
                call.respond(Text("error"))
            }
        }

    }
}

@Serializable
data class CommentsListADD(
    val lessonID: Int,
    val token: String,
    val content: String,
    val sendingDateTime: String,
)


