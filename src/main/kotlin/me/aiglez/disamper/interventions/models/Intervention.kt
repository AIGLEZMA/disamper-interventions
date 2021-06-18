package me.aiglez.disamper.interventions.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date
import tornadofx.*

class Intervention(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Intervention>(Interventions)

    var lastName by Interventions.lastName
    var firstName by Interventions.firstName
    var functions by Interventions.functions

    var client by Interventions.client
    var date by Interventions.date

    var note by Interventions.note
}

class InterventionModel: ItemViewModel<Intervention>() {

    val id = bind(Intervention::id)
    val lastName = bind(Intervention::lastName)
    val firstName = bind(Intervention::firstName)
    val functions = bind(Intervention::functions)

    val client = bind(Intervention::client)
    val date = bind(Intervention::date)

    val note = bind(Intervention::note)
}

object Interventions : IntIdTable() {

    //val interventionId = integer("intervention-id")
    val lastName = varchar("last-name", 64)
    val firstName = varchar("first-name", 64)
    val functions = varchar("functions", 10000)
    val client = varchar("client", 10000)
    val date = date("date")

    val note = varchar("note", 1000000).nullable()

}