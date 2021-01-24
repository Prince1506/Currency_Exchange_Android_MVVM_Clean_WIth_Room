package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo


data class Task(
    var id: Int,
    var description: String,
    var scheduledDate: String,
    var status: String
) {
    fun toEntity(): TaskResponseEntity {
        return TaskResponseEntity(
            id,
            description,
            scheduledDate,
            status,
            null,
            null
        )
    }
}
