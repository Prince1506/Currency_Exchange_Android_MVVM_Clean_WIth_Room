package com.mvvm_clean.currency_exchange.features.canada_facts.data.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//const val tableName = "TABLE_TASK_LIST"
//
//@Entity(tableName = tableName)
data class TaskResponseEntity (
    @PrimaryKey(autoGenerate = true)
//    @SerializedName("id")
//    @Expose
    var id: Int,
//    @SerializedName("description")
//    @Expose
    var description: String,
//    @SerializedName("scheduledDate")
//    @Expose
    var scheduledDate: String,
//    @SerializedName("status")
//    @Expose
    var status: String,
    val quotessd: Map<String?, Double?>?,
    val quotes: Date?
){
    fun toModel(): Task {
        return Task(
            id,
            description,
            scheduledDate,
            status
        )
    }

}