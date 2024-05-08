package com.example.database


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UsersBobi : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val username: Column<String> = varchar("username", length = 24)
    val login: Column<String> = varchar("login", length = 24)
    val password: Column<String> = varchar("password", length = 24)
    val token: Column<String> = varchar("token", length = 64)

    override val primaryKey = PrimaryKey(id) // name is optional here
}

object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val username: Column<String> = varchar("username", length = 24)
    val login: Column<String> = varchar("login", length = 24)
    val password: Column<String> = varchar("password", length = 24)
    val token: Column<String> = varchar("token", length = 64)

    override val primaryKey = PrimaryKey(id) // name is optional here
}

object Comments : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val lessonId: Column<Int> = (integer("lesson_id"))
    val username: Column<String> = (varchar("username", length = 64))
    val content: Column<String?> = varchar("content", length = 2000).nullable()
    val sendingDateTime: Column<String> = varchar("sending_date", length = 64)

    override val primaryKey = PrimaryKey(id)
}

object Schedule : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val group: Column<String> = varchar("group_name", length = 50)
    val timeStart: Column<String> = varchar("time_start", length = 16)
    val timeEnd: Column<String> = varchar("time_end", length = 16)
    val name: Column<String> = varchar("name", 100)
    val type: Column<String> = varchar("type", length = 50)
    val place: Column<String> = varchar("place", length = 100)
    val teacher: Column<String> = varchar("teacher", length = 100)
    val date: Column<String> = varchar("date", length = 50)
    val pairNumber: Column<Int> = integer("pair_number")
    val homework: Column<String?> = varchar("homework", length = 2000).nullable()

    override val primaryKey = PrimaryKey(id)
}