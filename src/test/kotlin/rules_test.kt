import com.pois.lab1.Interaction
import com.pois.lab1.data.Company
import com.pois.lab1.data.Consumer
import com.pois.lab1.data.TariffPlan
import org.drools.core.impl.InternalKnowledgeBase
import org.drools.core.impl.KnowledgeBaseFactory
import org.junit.Test
import org.kie.api.io.ResourceType
import org.kie.api.runtime.KieSession
import org.kie.internal.builder.KnowledgeBuilderFactory
import org.kie.internal.io.ResourceFactory
import java.io.File
import kotlin.test.assertEquals

class RulesTest() {
    @Test
    fun test1() {
        try {
            val base = prepareKnowledgeBase(listOf(
                File("rules/agreConc.drl")
            ))
            val ksession = base.newKieSession()
            loadFirstDataIntoSession(ksession)
            ksession.fireAllRules()
            val inter = ksession.getGlobal("interaction") as Interaction
            assertEquals(440.0F, inter.getCost())
            assertEquals("Fedor", inter.getConsumer().name)
            assertEquals(20F, inter.getConsumer().tariffPlan.gigabyte)
            assertEquals(500, inter.getConsumer().tariffPlan.messages)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
    @Test
    fun test2() {
        try {
            val base = prepareKnowledgeBase(listOf(
                File("rules/agreConc.drl")
            ))
            val ksession = base.newKieSession()
            loadSecondDataIntoSession(ksession)
            ksession.fireAllRules()
            val inter = ksession.getGlobal("interaction") as Interaction
            assertEquals(520.0F, inter.getCost())
            assertEquals("Alexey", inter.getConsumer().name)
            assertEquals(30F, inter.getConsumer().tariffPlan.gigabyte)
            assertEquals(600, inter.getConsumer().tariffPlan.messages)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
    private fun loadFirstDataIntoSession(kSession: KieSession) {
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
    private fun loadSecondDataIntoSession(kSession: KieSession) {
        val tariff = TariffPlan(
            "MTC",
            "Efficient",
            false,
            30F,
            200,
            200,
            600
        )
        val consumer = Consumer(
            "Alexey",
            28,
            tariff
        )
        val company = Company(
            "MTC",
            listOf(tariff),
            listOf(consumer)
        )
        kSession.setGlobal("interaction", Interaction())
        kSession.insert(tariff)
        kSession.insert(consumer)
        kSession.insert(company)
    }
    private fun prepareKnowledgeBase(files: List<File>): InternalKnowledgeBase {
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
}