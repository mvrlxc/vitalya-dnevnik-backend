package com.example.database

import com.example.schedule.CommentsList
import com.example.schedule.ScheduleFull
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object DAO {

    fun insertUser(
        userLogin: String,
        userPassword: String,
        userUsername: String,
    ): String {
        var userToken = ""
        transaction {
            userToken = UsersBobi.insert {
                it[username] = userUsername
                it[login] = userLogin
                it[password] = userPassword
                it[token] = UUID.randomUUID().toString()
            } get UsersBobi.token
        }
        return userToken
    }

    fun isLoginFree(userLogin: String): Boolean {
        var a = -1
        transaction {
            UsersBobi.select(UsersBobi.id).where { UsersBobi.login.eq(userLogin) }.forEach { a = it[UsersBobi.id] }
        }
        return a == -1
    }

    fun getToken(
        userLogin: String,
        userPassword: String,
    ): String {
        var userToken = ""
        transaction {
            UsersBobi.select(UsersBobi.token).where { UsersBobi.login.eq(userLogin).and { UsersBobi.password.eq(userPassword) } }
                .forEach { userToken = it[UsersBobi.token] }
        }
        return userToken
    }

    fun getUsername(token: String): String {
        var usernameX: String = ""
        transaction {
            UsersBobi.select(UsersBobi.username).where { UsersBobi.token.eq(token) }.forEach { usernameX = it[UsersBobi.username] }
        }
        return usernameX
    }

    fun getAllDates(): List<String> {
        val list: MutableList<String> = mutableListOf()
        transaction {
            Schedule.select(Schedule.date).withDistinct(true).sortedBy { it[Schedule.date] }
                .forEach { list.add(it[Schedule.date]) }
        }
        return list
    }

    fun getSchedule(): MutableList<ScheduleFull> {
        val schedule: MutableList<ScheduleFull> = mutableListOf()
        transaction {
            Schedule.selectAll().forEach {
                schedule.add(
                    ScheduleFull(
                        id = it[Schedule.id],
                        timeStart = it[Schedule.timeStart],
                        timeEnd = it[Schedule.timeEnd],
                        name = it[Schedule.name],
                        type = it[Schedule.type],
                        place = it[Schedule.place],
                        teacher = it[Schedule.teacher],
                        pairNumber = it[Schedule.pairNumber],
                        date = it[Schedule.date],
                        group = it[Schedule.group],
                        homework = it[Schedule.homework] ?: "null"
                    )
                )
            }
        }
        return schedule
    }

    fun getComments(lessonId: Int): MutableList<CommentsList> {
        val comments: MutableList<CommentsList> = mutableListOf()
        transaction {
            Comments.selectAll().where { Comments.lessonId.eq(lessonId) }.forEach {
                comments.add(
                    CommentsList(
                        username = it[Comments.username],
                        lessonID = it[Comments.lessonId],
                        content = it[Comments.content] ?: "null",
                        sendingDateTime = it[Comments.sendingDateTime]
                    )
                )
            }
        }
        return comments
    }

    fun addComment(token: String, lessonIDX: Int, contentX: String, sendingDateTimeX: String) {
        val usernameX = getUsername(token)
        transaction {
            Comments.insert {
                it[lessonId] = lessonIDX
                it[username] = usernameX
                it[content] = contentX
                it[sendingDateTime] = sendingDateTimeX

            }
        }
    }


}