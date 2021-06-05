package me.aiglez.disamper.interventions.models

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.StringProperty
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

class Intervention(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Intervention>(Interventions)

    var lastName by Interventions.lastName
    var firstName by Interventions.firstName
    var functions by Interventions.functions

    var client by Interventions.client
    var date by Interventions.date

    var note by Interventions.note

}

class InterventionModel(
    private val id: IntegerProperty, private val lastName: StringProperty,
    private val firstName: StringProperty, private val functions: StringProperty,
    private val client: StringProperty, private val date: SimpleObjectProperty<LocalDate>,
    private val note: StringProperty?
    ): RecursiveTreeObject<Intervention>() {

    fun idProperty(): IntegerProperty = id

    fun lastNameProperty(): StringProperty = lastName

    fun firstNameProperty(): StringProperty = firstName

    fun functionsProperty(): StringProperty = functions

    fun clientProperty(): StringProperty = client

    fun dateProperty(): SimpleObjectProperty<LocalDate> = date

    fun noteProperty(): StringProperty? = note

}

/**
 * class InterventionModel: ItemViewModel<Intervention>() {

val lastName = bind(Intervention::lastName)
val firstName = bind(Intervention::firstName)
val functions = bind(Intervention::functions)

val client = bind(Intervention::client)
val date = bind(Intervention::date)

val note = bind(Intervention::note)

}
 */

object Interventions : IntIdTable() {

    val lastName = varchar("last-name", 64)
    val firstName = varchar("first-name", 64)
    val functions = varchar("functions", 10000)
    val client = varchar("client", 10000)
    val date = date("date")

    val note = varchar("note", 1000000).nullable()

}