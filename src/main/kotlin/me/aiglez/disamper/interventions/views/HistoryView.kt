package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory
import javafx.collections.transformation.FilteredList
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.text.Font
import me.aiglez.disamper.interventions.controllers.DatabaseController
import me.aiglez.disamper.interventions.models.InterventionModel
import me.aiglez.disamper.interventions.utils.jfxbutton
import me.aiglez.disamper.interventions.utils.jfxtextfield
import tornadofx.*


class HistoryView : View("DISAMPER") {

    private val database: DatabaseController by inject()

    private lateinit var table: TableView<InterventionModel>
    private lateinit var printButton : JFXButton
    private lateinit var saveButton : JFXButton
    private lateinit var deleteButton : JFXButton

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
            jfxbutton("AJOUTER", JFXButton.ButtonType.RAISED) {
                prefHeight = 37.0; prefWidth = 107.0

                ripplerFill = c("#66BB6A")
                textFill = c("#FFFFFF")
                font = Font.font("Segoe UI Semibold", 15.0)

                graphic = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS).apply {
                    fill = c("#FFFFFF")
                }

                stackpaneConstraints {
                    alignment = Pos.CENTER_RIGHT
                    marginRight = 20.0
                }

                style {
                    backgroundColor = multi(c("#54a957"))
                }

                action {
                    replaceWith<AddView>()
                }
            }

            // icon
            add(
                FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.BOLT, "50").apply {
                    fill = c("#5865F2")
                    effect = DropShadow(10.0, c("#d8d8d8"))
                    onHover {
                        style = if (it) {
                            String.format(
                                "-fx-font-family: %s; -fx-font-size: %s;",
                                FontAwesomeIcon.BOLT.fontFamily(),
                                60
                            )
                        } else {
                            String.format(
                                "-fx-font-family: %s; -fx-font-size: %s;",
                                FontAwesomeIcon.BOLT.fontFamily(),
                                50
                            )
                        }
                    }
                }
            )
        }

        pane {
            prefHeight = 610.0; prefWidth = 1000.0
            layoutY = 90.0

            val filteredList = FilteredList(database.cache) { true }

            borderpane {
                prefHeight = 500.0; prefWidth = 900.0
                layoutX = 50.0; layoutY = 52.0

                left {
                    text("HISTORIQUE") {
                        fill = c("#5865F2")
                        font = loadFont("/fonts/roboto-regular.ttf", 28)
                    }
                }

                right {
                    jfxtextfield {
                        prefHeight = 30.0; prefWidth = 190.0

                        focusColor = c("#5865F2")
                        unFocusColor = c("#dbdbdb")
                        promptText = "RECHERCHER..."
                        isLabelFloat = true

                        // we disable buttons and remove selection
                        focusedProperty().addListener { _, _, newValue ->
                            if (newValue) {
                                table.selectionModel.clearSelection()

                                updateButtons()
                            }
                        }

                        textProperty().addListener { _, _, newValue ->
                            filteredList.setPredicate { i ->
                                if (newValue.isInt()) {
                                    val intVal = newValue.toInt()
                                    intVal == i.id.value.value
                                            || i.client.value.contains(newValue)
                                            || intVal == i.date.value.year
                                            || intVal == i.date.value.monthValue
                                            || intVal == i.date.value.dayOfMonth
                                } else {
                                    i.lastName.value.contains(newValue, true)
                                            || i.firstName.value.contains(newValue, true)
                                            || i.functions.value.contains(newValue, true)
                                            || i.client.value.contains(newValue, true)
                                            || i?.note?.value?.contains(newValue, true) ?: false

                                }
                            }
                        }
                    }
                }
            }

            borderpane {
                layoutX = 50.0; layoutY = 535.0
                prefHeight = 35.0; prefWidth = 900.0

                left {
                    saveButton = jfxbutton("SAUVEGARDER", JFXButton.ButtonType.RAISED) {
                        prefHeight = 35.0; prefWidth = 290.0

                        ripplerFill = c("#66BB6A")
                        textFill = c("#FFFFFF")
                        font = Font.font("Segoe UI Semibold", 15.0)

                        graphic = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SAVE)
                            .apply {
                                fill = c("#FFFFFF")
                            }

                        style {
                            backgroundColor = multi(c("#54a957"))
                        }

                        isDisable = true
                    }
                }

                center {
                    printButton = jfxbutton("IMPRIMER", JFXButton.ButtonType.RAISED) {
                        prefHeight = 35.0; prefWidth = 290.0

                        ripplerFill = c("#7784ff")
                        textFill = c("#FFFFFF")
                        font = Font.font("Segoe UI Semibold", 15.0)

                        graphic = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PRINT)
                            .apply {
                                fill = c("#FFFFFF")
                            }

                        style {
                            backgroundColor = multi(c("#5865F2"))
                        }

                        borderpaneConstraints {
                            alignment = Pos.CENTER
                        }

                        isDisable = true
                    }
                }

                right {
                    deleteButton = jfxbutton("SUPPRIMER", JFXButton.ButtonType.RAISED) {
                        prefHeight = 35.0; prefWidth = 290.0

                        ripplerFill = c("#E57373")
                        textFill = c("#FFFFFF")
                        font = Font.font("Segoe UI Semibold", 15.0)

                        graphic = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REMOVE)
                            .apply {
                                fill = c("#FFFFFF")
                            }

                        style {
                            backgroundColor = multi(c("#D32F2F"))
                        }

                        action {
                            runAsync {
                                database.deleteIntervention(table.selectedItem!!)
                                table.selectionModel.clearSelection()
                                updateButtons()
                            }
                        }

                        isDisable = true
                    }
                }
            }

            table = tableview(filteredList) {
                layoutX = 50.0; layoutY = 104.0
                prefHeight = 415.0; prefWidth = 900.0

                placeholder = label("Aucune interventions sur la base de données") {
                    font = loadFont("/fonts/roboto-regular.ttf", 13)
                }

                onUserSelect {
                    updateButtons()
                }

                column("ID", InterventionModel::id) {
                    correct(true, this)
                    minWidth(25.0)
                    maxWidth(25.0)
                }
                column("NOM", InterventionModel::lastName) {
                    correct(true, this)
                    minWidth(130.0)
                }
                column("PRÉNOM", InterventionModel::firstName) {
                    correct(true, this)
                    minWidth(130.0)
                }
                column("FONCTION(s)", InterventionModel::functions) {
                    correct(true, this)
                    minWidth(130.0)
                }
                column("CLIENT", InterventionModel::client) {
                    correct(true, this)
                    minWidth(130.0)
                }
                column("DATE", InterventionModel::date) {
                    correct(true, this)
                    minWidth(130.0)
                }
                column("NOTE", InterventionModel::note) {
                    correct(false, this)
                    minWidth(220.0)
                }

            }
        }
    }

    private fun correct(sortable: Boolean, column: TableColumn<*,*>) {
        column.isReorderable = false
        column.isResizable = false
        column.isSortable = sortable
    }

    private fun updateButtons() {
        if (table.selectedItem == null) {
            printButton.isDisable = true
            saveButton.isDisable = true
            deleteButton.isDisable = true
        } else {
            printButton.isDisable = false
            saveButton.isDisable = false
            deleteButton.isDisable = false
        }
    }
}