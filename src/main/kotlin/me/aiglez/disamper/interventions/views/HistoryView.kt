package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import javafx.collections.transformation.FilteredList
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Modality
import me.aiglez.disamper.interventions.controllers.DatabaseController
import me.aiglez.disamper.interventions.controllers.DocumentController
import me.aiglez.disamper.interventions.folder
import me.aiglez.disamper.interventions.models.InterventionModel
import me.aiglez.disamper.interventions.utils.jfxbutton
import me.aiglez.disamper.interventions.utils.jfxtextfield
import tornadofx.*


class HistoryView : View("DISAMPER") {

    private val database: DatabaseController by inject()
    private val document: DocumentController by inject()

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

                        style {
                            backgroundColor = multi(c("#54a957"))
                        }

                        action {
                            val dir = chooseDirectory("Emplacement du document", folder)
                            if (dir != null) {
                                runAsyncWithOverlay {
                                    document.save(table.selectedItem!!.item, dir)


                                }.setOnSucceeded {
                                    JFXAlert<String>(this.scene.window).apply {
                                        initModality(Modality.APPLICATION_MODAL)
                                        isOverlayClose = false

                                        setContent(JFXDialogLayout().apply {
                                            isHideOnEscape = true

                                            setBody(label(
                                                "Le document PDF a bien été enregister."
                                            ))
                                            setActions(
                                                jfxbutton("OK", JFXButton.ButtonType.RAISED) {
                                                    style {
                                                        backgroundColor = multi(c("#5865F2"))
                                                        textFill = Color.web("#FFFFFF")
                                                        font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                                    }
                                                    action {
                                                        hideWithAnimation()
                                                    }
                                                    styleClass.add("dialog-accept")
                                                },
                                            )
                                        })

                                        show()
                                    }
                                }
                            }
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

                        style {
                            backgroundColor = multi(c("#5865F2"))
                        }

                        borderpaneConstraints {
                            alignment = Pos.CENTER
                        }

                        action {
                            runAsync {
                                document.print(table.selectedItem!!.item)

                                success {
                                    JFXAlert<String>(this@borderpane.scene.window).apply {
                                        initModality(Modality.APPLICATION_MODAL)
                                        isOverlayClose = false

                                        setContent(JFXDialogLayout().apply {
                                            isHideOnEscape = true

                                            setBody(
                                                label(
                                                    "Le document PDF a bien été imprimer."
                                                )
                                            )
                                            setActions(
                                                jfxbutton("OK", JFXButton.ButtonType.RAISED) {
                                                    style {
                                                        backgroundColor = multi(c("#5865F2"))
                                                        textFill = Color.web("#FFFFFF")
                                                        font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                                    }
                                                    action {
                                                        hideWithAnimation()
                                                    }
                                                    styleClass.add("dialog-accept")
                                                },
                                            )
                                        })

                                        show()
                                    }
                                }

                                fail {
                                    JFXAlert<String>(this@borderpane.scene.window).apply {
                                        initModality(Modality.APPLICATION_MODAL)
                                        isOverlayClose = false

                                        setContent(JFXDialogLayout().apply {
                                            isHideOnEscape = true

                                            setBody(
                                                label(
                                                    "L'impression du document a échoué, Veuillez connecter" +
                                                            " l'imprimante à vote ordinateur"
                                                )
                                            )
                                            setActions(
                                                jfxbutton("FERMER", JFXButton.ButtonType.RAISED) {
                                                    style {
                                                        backgroundColor = multi(c("#5865F2"))
                                                        textFill = Color.web("#FFFFFF")
                                                        font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                                    }
                                                    action {
                                                        hideWithAnimation()
                                                    }
                                                    styleClass.add("dialog-accept")
                                                },
                                            )
                                        })

                                        show()
                                    }
                                }
                            }
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

                        style {
                            backgroundColor = multi(c("#D32F2F"))
                        }

                        action {
                            JFXAlert<String>(this.scene.window).apply {
                                initModality(Modality.APPLICATION_MODAL)
                                isOverlayClose = false

                                setContent(JFXDialogLayout().apply {
                                    isHideOnEscape = true

                                    setBody(
                                        label(
                                            "Êtes-vous sûr de vouloir supprimer cette intervention définitivement"
                                        )
                                    )
                                    setActions(
                                        jfxbutton("OUI", JFXButton.ButtonType.RAISED) {
                                            style {
                                                backgroundColor = multi(c("#5865F2"))
                                                textFill = Color.web("#FFFFFF")
                                                font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                            }
                                            action {
                                                runAsync {
                                                    database.deleteIntervention(table.selectedItem!!)
                                                    table.selectionModel.clearSelection()
                                                    updateButtons()
                                                }

                                                hideWithAnimation()
                                            }
                                            styleClass.add("dialog-accept")
                                        },
                                    )
                                })

                                show()
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