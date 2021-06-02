package me.aiglez.disamper.interventions.mvc

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainController : Controller() {

    val lastNameProperty = stringProperty()
    val firstNameProperty = stringProperty()
    val functionsProperty = stringProperty()

    val clientProperty = stringProperty()
    val dateProperty = SimpleObjectProperty<LocalDate>()
    val noteProperty = stringProperty()

    @OptIn(ExperimentalStdlibApi::class)
    fun save() {
        println("-------------------------------------------------------")
        println("Saving PDF file...")
        println("Full Name: ${lastNameProperty.value.uppercase()} ${firstNameProperty.value.lowercase().capitalize()}")
        println("Functions: ${functionsProperty.value} | Date: ${format(dateProperty.value)}")
        if (noteProperty.isNotNull.value) {
            println("Note: ${noteProperty.value}")
        }

        Thread.sleep(3000)
        println("Done!")
        println("-------------------------------------------------------")
        reset()
    }

    fun canSave(): Boolean {
        return lastNameProperty.isNotNull.value && firstNameProperty.isNotNull.value && functionsProperty.isNotNull.value
                && clientProperty.isNotNull.value && dateProperty.isNotNull.value
    }

    fun print() { }

    fun reset() {
        lastNameProperty.set(null)
        firstNameProperty.set(null)
        functionsProperty.set(null)

        clientProperty.set(null)
        dateProperty.set(null)
        noteProperty.set(null)
    }

    companion object {

        private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        private fun format(localDate: LocalDate): String = formatter.format(localDate)

    }

}
