package com.example.schedule

import com.example.database.DAO
import kotlinx.serialization.Serializable

object ScheduleMethods {

    fun getSchedule(id: String): MutableList<ScheduleFull> {

        val response = DAO.getSchedule()
        return response
    }

}

@Serializable
data class ScheduleFull(
    val id: Int,
    val group: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val type: String,
    val place: String,
    val teacher: String,
    val date: String,
    val pairNumber: Int,
)

