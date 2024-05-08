package com.example.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DB {
    fun connect() {
        Database.connect(
            "jdbc:postgresql://35.237.169.76/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "password"
        )
    }

    fun createTables() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(UsersBobi, Comments, Schedule, Users)
        }
    }

}