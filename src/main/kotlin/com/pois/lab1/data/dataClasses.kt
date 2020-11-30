package com.pois.lab1.data

data class Consumer(
    val name: String,
    val age: Int,
    val tariffPlan: TariffPlan
)

data class TariffPlan(
    val companyOwnerName: String,
    val name: String,
    val isOnlyHome: Boolean = false,
    val gigabyte: Float,
    val minuteOnSame: Int,
    val minuteOnDifferent: Int,
    val messages: Int
)

data class Company(
    val name: String,
    val tariffList: List<TariffPlan>,
    val consumerList: List<Consumer>
)
