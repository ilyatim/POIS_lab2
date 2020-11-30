package com.pois.lab1

import com.pois.lab1.data.Company
import com.pois.lab1.data.Consumer
import com.pois.lab1.data.TariffPlan
import org.kie.api.cdi.KSession
import org.kie.api.runtime.KieSession

fun loadDataIntoSession(kSession: KieSession) {
    val tariff = TariffPlan(
        "Beeline",
        "Super efficient",
        false,
        20F,
        200,
        200,
        500
    )
    val consumer = Consumer(
        "Fedor",
        22,
        tariff
        )
    val company = Company(
        "Beeline",
        listOf(tariff),
        listOf(consumer)
    )
    kSession.setGlobal("interaction", Interaction())
    kSession.insert(tariff)
    kSession.insert(consumer)
    kSession.insert(company)
}