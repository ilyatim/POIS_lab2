package com.pois.lab1

import com.pois.lab1.data.Consumer

class Interaction {

    private var cost: Float = 0F
    private lateinit var consumer: Consumer

    fun printlnTheCost(consumer: Consumer, cost: Float) {
        println("Consumer - ${consumer.name}, age - ${consumer.age}")
        println("Current plan name - ${consumer.tariffPlan.name}")
        println("Parameters: gigabyte - ${consumer.tariffPlan.gigabyte}")
        println("Minute on the same numbers - ${consumer.tariffPlan.minuteOnSame}")
        println("Minute on the different numbers - ${consumer.tariffPlan.minuteOnDifferent}")
        println("Total cost of your plan - $cost")
        this.cost = cost
        this.consumer = consumer
    }

    fun getCost(): Float = cost
    fun getConsumer(): Consumer = consumer
}