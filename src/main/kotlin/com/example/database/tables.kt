package com.example.database


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Polsovateli : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val username: Column<String> = varchar("imya", length = 24)
    val login: Column<String> = varchar("lagin", length = 24)
    val password: Column<String> = varchar("parol", length = 24)
    val token: Column<String> = varchar("token", length = 64)

    override val primaryKey = PrimaryKey(id, name = "PK_ID") // name is optional here
}

object Comments : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val userToken: Column<String> = (varchar("user_token", length = 64))
    val ScheduleId: Column<Int> = (integer("schedule_id"))
    val content: Column<String?> = varchar("content", length = 2000).nullable()
    val progress: Column<String?> = varchar("progress", length = 16).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Comments_ID")
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

    override val primaryKey = PrimaryKey(id, name = "PK_Schedule_ID")
}