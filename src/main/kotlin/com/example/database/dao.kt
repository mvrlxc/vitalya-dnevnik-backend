package com.example.database

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
            userToken = Polsovateli.insert {
                it[username] = userUsername
                it[login] = userLogin
                it[password] = userPassword
                it[token] = UUID.randomUUID().toString()
            } get Polsovateli.token
        }
        return userToken
    }

    fun isLoginFree(userLogin: String): Boolean {
        var a = -1
        transaction {
            Polsovateli.select(Polsovateli.id).where { Polsovateli.login.eq(userLogin) }.forEach { a = it[Polsovateli.id] }
        }
        return a == -1
    }

    fun getToken(
        userLogin: String,
        userPassword: String,
    ): String {
        var userToken = ""
        transaction {
            Polsovateli.select(Polsovateli.token).where { Polsovateli.login.eq(userLogin).and { Polsovateli.password.eq(userPassword) } }
                .forEach { userToken = it[Polsovateli.token] }
        }
        return userToken
    }

    fun getUsername(token: String): String {
        var usernameX: String = ""
        transaction {
            Polsovateli.select(Polsovateli.username).where { Polsovateli.token.eq(token) }.forEach { usernameX = it[Polsovateli.username] }
        }
        return usernameX
    }

    fun insert() {
        transaction {
            Schedule.insert {
                it[group] = "PI06"
                it[timeStart] = "8:30"
                it[timeEnd] = "10:00"
                it[name] = "vishmat"
                it[type] = "praktik"
                it[place] = "3 korp - 404"
                it[teacher] = "mochalina"
                it[date] = "25.04.2024"
                it[pairNumber] = 3
            }
            Schedule.insert {
                it[group] = "PI06"
                it[timeStart] = "10:10"
                it[timeEnd] = "11:40"
                it[name] = "jopa penis chlen"
                it[type] = "lekcia"
                it[place] = "2 korp - 228"
                it[teacher] = "negroid"
                it[date] = "25.04.2024"
                it[pairNumber] = 4
            }
            Schedule.insert {
                it[group] = "PI06"
                it[timeStart] = "9:30"
                it[timeEnd] = "10:10"
                it[name] = "neeagr"
                it[type] = "praktic"
                it[place] = "3korp - 322"
                it[teacher] = "pidoras"
                it[date] = "26.04.2024"
                it[pairNumber] = 1
            }
        }
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
                        group = it[Schedule.group]
                    )
                )
            }
        }
        return schedule
    }


}