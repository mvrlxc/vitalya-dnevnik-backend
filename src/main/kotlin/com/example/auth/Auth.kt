package com.example.auth

import com.example.database.DAO

object Auth {

    fun register(login: String, password: String, username: String): String {
        val isLoginFree = DAO.isLoginFree(login)
        return if (isLoginFree) {
            DAO.insertUser(login, password, username)
        } else "login already exists"
    }


    fun login(login: String, password: String): String {
        val token = DAO.getToken(login, password)
        val isLoginFree = DAO.isLoginFree(login)
        return if (token.isNotEmpty() && !isLoginFree) {
            token
        } else if (token.isNotEmpty() && isLoginFree) {
            "invalid login"
        } else {
            "invalid password"
        }

    }

}