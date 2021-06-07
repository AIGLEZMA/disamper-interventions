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
import java.time.LocalDate

class MainController: Controller() {

    val interventions: ObservableList<InterventionModel> by lazy {
        transaction {
            Intervention.all().map {
                InterventionModel().apply {
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

            Intervention.new {
                lastName = "AIT NASSER"
                firstName = "Jaouad"
                functions = "Gérant"

                client = "Nadia LLC"
                date = LocalDate.of(2021, 2, 5)
            }

            Intervention.new {
                lastName = "AIT NASSER"
                firstName = "Asma"
                functions = "Co-Gérant"

                client = "Nadia LLC"
                date = LocalDate.of(2021, 2, 8)
            }

            Intervention.new {
                lastName = "BALHAN"
                firstName = "Fatima"
                functions = "Technicien"

                client = "Google"
                date = LocalDate.of(2020, 5, 10)
            }
        }
    }

}