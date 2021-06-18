package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.*
import com.jfoenix.validation.RegexValidator
import com.jfoenix.validation.RequiredFieldValidator
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Modality
import me.aiglez.disamper.interventions.controllers.DatabaseController
import me.aiglez.disamper.interventions.utils.jfxbutton
import me.aiglez.disamper.interventions.utils.jfxdatepicker
import me.aiglez.disamper.interventions.utils.jfxtextarea
import me.aiglez.disamper.interventions.utils.jfxtextfield
import tornadofx.*

class AddView : View("DISAMPER") {

    private val database: DatabaseController by inject()

    private lateinit var lastNameField: JFXTextField
    private lateinit var firstNameField: JFXTextField
    private lateinit var functionsField: JFXTextField
    private lateinit var clientField: JFXTextField
    private lateinit var datePicker: JFXDatePicker
    private lateinit var noteArea: JFXTextArea


    @Suppress("DuplicatedCode")
    override val root = pane {
        style {
            backgroundColor = multi(c("#fafafa"))
        }

        stackpane {
            prefHeight = 90.0; prefWidth = 1000.0
            isCenterShape = false
            style {
                backgroundColor = multi(Color.WHITESMOKE)
            }
            effect = DropShadow(35.0, c("#d8d8d8"))

            // text
            text("DISAMPER") {
                fill = c("#5865F2")
                font = loadFont("/fonts/roboto-medium.ttf", 29)
                effect = DropShadow(10.0, c("#d8d8d8"))

                stackpaneConstraints {
                    alignment = Pos.CENTER_LEFT
                    marginLeft = 20.0
                }
            }

            // button
            jfxbutton("HISTORIQUE", JFXButton.ButtonType.RAISED) {
                prefHeight = 37.0; prefWidth = 133.0

                ripplerFill = c("#66BB6A")
                textFill = c("#FFFFFF")
                font = Font.font("Segoe UI Semibold", 15.0)

                stackpaneConstraints {
                    alignment = Pos.CENTER_RIGHT
                    marginRight = 20.0
                }

                style {
                    backgroundColor = multi(c("#54a957"))
                }

                action {
                    clearFields()
                    replaceWith<HistoryView>()
                }
            }
        }

        stackpane {
            prefHeight = 550.0; prefWidth = 560.0
            layoutX = 220.0; layoutY = 122.0

            pane {
                prefHeight = 550.0; prefWidth = 560.0

                style {
                    backgroundColor = multi(Color.WHITE)
                }

                effect = DropShadow(100.0, c("#e8e8e8"))

                text("FICHE D'INTERVENTION") {
                    layoutX = 148.0; layoutY = 53.0

                    fill = c("#5865F2")
                    font = loadFont("/fonts/roboto-regular.ttf", 25)
                }

                // last name field
                lastNameField = jfxtextfield(labelFloat = true, promptText = "NOM *") {
                    correct(true, 14.0, 121.0, this)
                }

                // first name field
                firstNameField = jfxtextfield(labelFloat = true, promptText = "PRÉNOM *") {
                    correct(true, 326.0, 121.0, this)
                }

                // client field
                clientField = jfxtextfield(labelFloat = true, promptText = "CLIENT *") {
                    correct(false, 326.0, 198.0, this)
                }

                // functions field
                functionsField = jfxtextfield(labelFloat = true, promptText = "FONCTION(s) *") {
                    correct(false, 14.0, 198.0, this)
                }

                // date picker
                datePicker = jfxdatepicker() {
                    prefHeight = 29.0; prefWidth = 534.0
                    layoutX = 14.0; layoutY = 275.0

                    promptText = "DATE *"
                    defaultColor = c("#5865F2")
                    isOverLay = true
                    dialogParent = this@stackpane

                    validators.add(RequiredFieldValidator("Champ requis"))

                    focusedProperty().addListener { _, _, newVal ->
                        if (!newVal) validate()
                    }
                }

                // note or observation area
                noteArea = jfxtextarea() {
                    prefHeight = 70.0; prefWidth = 533.0
                    layoutX = 14.0; layoutY = 339.0

                    promptText = "NOTE OU AUTRES..."
                    focusColor = c("#5865F2")
                    unFocusColor = c("#dbdbdb")
                    font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                    isLabelFloat = true
                }


                // save button
                jfxbutton("SAUVEGARDER", JFXButton.ButtonType.RAISED) {
                    correct(14.0, 467.0, "#54a957", "#66BB6A", this)

                    action {
                        if (lastNameField.text.isNullOrEmpty()
                            || firstNameField.text.isNullOrEmpty()
                            || functionsField.text.isNullOrEmpty()
                            || clientField.text.isNullOrEmpty()
                            || datePicker.value == null
                        ) {
                            val childrens = this@pane.childrenUnmodifiable
                            for (child in childrens) {
                                if (child is JFXTextField) {
                                    child.validate()
                                } else if (child is JFXDatePicker) {
                                    child.validate()
                                }
                            }
                            return@action
                        }
                        runAsync {
                            database.createIntervention(
                                lastNameField.text, firstNameField.text, functionsField.text,
                                clientField.text, datePicker.value, noteArea.text
                            )
                            clearFields()
                        }
                    }
                }

                // print button
                jfxbutton("IMPRIMER", JFXButton.ButtonType.RAISED) {
                    correct(326.0, 467.0, "#5865F2", "#7784ff", this)

                    action {
                        if (!true) {
                            val childrens = this@pane.childrenUnmodifiable
                            for (child in childrens) {
                                if (child is JFXTextField) {
                                    child.validate()
                                } else if (child is JFXDatePicker) {
                                    child.validate()
                                }
                            }
                            return@action
                        }
                    }
                }

                // reset
                hyperlink ("Reset") {
                    prefHeight = 21.0; prefWidth = 38.0
                    layoutX = 508.0; layoutY = 507.0

                    action {
                        val alert: JFXAlert<String> = JFXAlert(this.scene.window)
                        alert.initModality(Modality.APPLICATION_MODAL)
                        alert.isOverlayClose = false
                        alert.setContent(JFXDialogLayout().apply {
                            setBody(label(
                                "Si vous procédez vous allez perdre toutes les informations"
                                        + "que vous avez rentrées au préalable"
                            ))
                            setActions(
                                jfxbutton("D'ACCORD", JFXButton.ButtonType.RAISED) {
                                    style {
                                        backgroundColor = multi(c("#4CAF50"))
                                        textFill = c("#FFFF")
                                        font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                    }

                                    action {
                                        //controller.reset()
                                        alert.hide()
                                    }
                                    styleClass.add("dialog-accept")
                                },
                            )
                        })
                        alert.show()
                    }
                }
            }
        }

        // shortcut : save (CTRL+S)
        shortcut("CTRL+S") {
            runAsync {
                //controller.save()
            }
        }

        // shortcut : save (CTRL+P)
        shortcut("CTRL+P") {
            println("Printing")
        }
    }

    private fun clearFields() {
        this.lastNameField.clear()
        this.firstNameField.clear()
        this.functionsField.clear()
        this.clientField.clear()
        this.datePicker.value = null
        this.noteArea.clear()
    }

    private fun correct(regex: Boolean, x: Double, y: Double, field: JFXTextField) {
        field.layoutX = x; field.layoutY = y
        field.prefHeight = 29.0; field.prefWidth = 220.0

        field.focusColor = c("#5865F2")
        field.unFocusColor = c("#dbdbdb")
        field.font = loadFont("/fonts/roboto-regular.ttf", 13.0)
        field.isLabelFloat = true

        field.validators += RequiredFieldValidator("Champ requis")
        if (regex) {
            field.validators += RegexValidator("Invalide !").apply { regexPattern = NAME_REGEX_PATTERN }
        }

        field.focusedProperty().addListener { _, _, newVal ->
            if (!newVal) field.validate()
        }
    }

    private fun correct(x: Double, y: Double, color: String, ripperFill: String, button: JFXButton) {
        button.layoutX = x; button.layoutY = y
        button.prefHeight = 34.0; button.prefWidth = 220.0

        button.ripplerFill = c(ripperFill)

        button.style {
            backgroundColor = multi(c(color))
            textFill = c("#FFFFFF")
            font = loadFont("/fonts/roboto-regular.ttf", 11.0)!!
        }
    }

    companion object {

        private const val NAME_REGEX_PATTERN = "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+\$"

    }
}