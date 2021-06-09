package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory
import javafx.collections.transformation.FilteredList
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.text.Font
import me.aiglez.disamper.interventions.WIDTH
import me.aiglez.disamper.interventions.controllers.DatabaseController
import me.aiglez.disamper.interventions.models.InterventionModel
import me.aiglez.disamper.interventions.utils.jfxbutton
import me.aiglez.disamper.interventions.utils.jfxtextfield
import tornadofx.*


class HistoryView : View("DISAMPER") {

    private val database: DatabaseController by inject()

    private lateinit var printButton: Button

    override val root = pane {
        style {
            backgroundColor = multi(c("#fafafa"))
        }

        add(HeaderFragment::class)

        pane {
            prefHeight = 610.0; prefWidth = WIDTH
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
                    jfxbutton("SAUVEGARDER", JFXButton.ButtonType.RAISED) {
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
                    }
                }

                center {
                    jfxbutton("IMPRIMER", JFXButton.ButtonType.RAISED) {
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
                            alignment = Pos.TOP_CENTER
                        }
                    }
                }

                right {
                    jfxbutton("SUPPRIMER", JFXButton.ButtonType.RAISED) {
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

                        isDisable = true
                    }
                }
            }

            tableview(filteredList) {
                layoutX = 50.0; layoutY = 104.0
                prefHeight = 415.0; prefWidth = 900.0

                setOnMouseClicked {
                    println("Clicked")
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
}