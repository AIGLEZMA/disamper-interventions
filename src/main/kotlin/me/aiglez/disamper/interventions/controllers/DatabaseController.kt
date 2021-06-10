package me.aiglez.disamper.interventions.controllers

import javafx.collections.ObservableList
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
import java.sql.Connection

class DatabaseController: Controller() {

    val cache: ObservableList<InterventionModel> by lazy {
        transaction {
            Intervention.all().map {
                InterventionModel().apply {
                    println("Intervention -> Model for $it")
                    item = it
                }
            }.asObservable()
        }
    }

    init {

        Database.connect("jdbc:sqlite:/data.db", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Interventions)
        }
    }

    fun deleteIntervention(model: InterventionModel) {
        transaction {
            model.item.delete()
        }
        cache.remove(model)

        println("Database - deleted an intervention (id: ${model.id.value})")
    }
}