package com.pois.lab1

import org.drools.core.impl.InternalKnowledgeBase
import org.drools.core.impl.KnowledgeBaseFactory
import org.kie.api.cdi.KSession
import org.kie.api.io.ResourceType
import org.kie.api.runtime.KieSession
import org.kie.internal.builder.KnowledgeBuilderFactory
import org.kie.internal.io.ResourceFactory
import java.io.File

fun main() {
    try {
        val base = readKnowledgeBase(listOf(
            File("rules/agreConc.drl")
        ))
        val ksession = base.newKieSession()
        loadDataIntoSession(ksession)
        ksession.fireAllRules()

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

/*
private fun show(kieSession: KieSession) {
    kieSession.se
}
*/

private fun readKnowledgeBase(files: List<File>): InternalKnowledgeBase {
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()

    files.forEach { kbuilder.add(ResourceFactory.newFileResource(it), ResourceType.DRL) }

    val errors = kbuilder.errors

    if (errors.size > 0) {
        for (error in errors) {
            System.err.println(error)
        }
        throw IllegalArgumentException("Could not parse knowledge.")
    }

    val kbase = KnowledgeBaseFactory.newKnowledgeBase()
    kbase.addPackages(kbuilder.knowledgePackages)

    return kbase
}
