package me.aiglez.disamper.interventions.controllers

import javafx.collections.ObservableList
import me.aiglez.disamper.interventions.cacheDir
import me.aiglez.disamper.interventions.models.Intervention
import me.aiglez.disamper.interventions.models.InterventionModel
import me.aiglez.disamper.interventions.models.Interventions
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.nio.file.Paths
import java.sql.Connection
import java.time.LocalDate
import java.util.*

class DatabaseController: Controller() {

    val cache: ObservableList<InterventionModel> by lazy {
        transaction {
            Intervention.all().map {
                InterventionModel().apply { item = it }
            }.asObservable()
        }
    }

    init {
        Database.connect("jdbc:sqlite:${Paths.get(cacheDir.toString(), "data.db")}", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Interventions)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun createIntervention(lastName: String, firstName: String, functions: String, client: String, date: LocalDate, note: String?): Intervention {
        var i: Intervention? = null
        transaction {
            val intervention = Intervention.new {
                this.lastName = lastName.uppercase()
                this.firstName = firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.FRANCE) else it.toString() }
                this.functions = functions.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                this.client = client.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                this.date = date
                this.note = note

                i = this
            }
            cache.add(InterventionModel().apply { item = intervention })
        }
        return i!!
    }

    fun deleteIntervention(model: InterventionModel) {
        transaction {
            model.item.delete()
        }
        cache.remove(model)
    }
}