package me.aiglez.disamper.interventions.mvc

import com.jfoenix.controls.*
import com.jfoenix.validation.RegexValidator
import com.jfoenix.validation.RequiredFieldValidator
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import me.aiglez.disamper.interventions.utils.*
import tornadofx.*


class MainView : View("Intervention - DISAMPER") {

    private val controller: MainController by inject()

    @Suppress("DuplicatedCode")
    override val root: StackPane = stackpane {
        vbox {
            //isResizable = false
            prefHeight = 700.0; prefWidth = 800.0

            // TOP
            stackpane {
                alignment = Pos.CENTER_LEFT
                prefHeight = 70.0; prefWidth = 800.0

                rectangle {
                    alignment = Pos.TOP_CENTER
                    height = 69.0 // not 70.0 to let some place for the shadow effect
                    width = 800.0

                    fill = Color.WHITESMOKE
                    strokeWidth = 0.0

                    style {
                        backgroundRadius = multi(box(10.px))
                    }

                    effect = DropShadow(20.0, Color.web("#d8d8d8"))
                }

                text("DISAMPER") {
                    alignment = Pos.CENTER_LEFT

                    font = loadFont("/fonts/roboto-medium.ttf", 25.0)
                    lineSpacing = 0.15

                    fill = Color.web("#00695c")
                    strokeWidth = 0.0

                    stackpaneConstraints {
                        margin = Insets(0.0, 0.0, 0.0, 20.0)
                    }
                }
            }

            // BOTTOM
            pane {
                prefHeight = 630.0; prefWidth = 800.0

                style {
                    backgroundColor = multi(Color.web("#FAFAFA"))
                }

                // MIDDLE PANE
                pane {
                    prefHeight = 550.0; prefWidth = 515.0
                    layoutX = 143.0; layoutY = 35.0

                    style {
                        backgroundColor = multi(Color.WHITE)
                    }

                    effect = DropShadow(100.0, Color.web("#e8e8e8"))

                    text("Fiche d'intervention") {
                        layoutX = 145.0; layoutY = 44.0

                        font = loadFont("/fonts/roboto-regular.ttf", 26.0)
                        lineSpacing = 0.15

                        fill = Color.web("#898989")
                        strokeWidth = 0.0
                    }

                    // last name field
                    jfxtextfield(labelFloat = true, promptText = "Nom *") {
                        bind(controller.lastNameProperty)
                        print("Bind done")

                        prefHeight = 29.0; prefWidth = 186.0
                        layoutX = 43.0; layoutY = 101.0

                        focusColor   = Color.web("#00796b")
                        unFocusColor = Color.web("#dbdbdb")
                        font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                        isLabelFloat = true

                        validators.addAll(
                            RequiredFieldValidator("Champ requis")
                                .apply { icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING) },
                            RegexValidator("Nom invalide")
                                .apply {
                                    icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING)
                                    regexPattern = NAME_REGEX_PATTERN
                                }
                        )

                        focusedProperty().addListener { _, _, newVal ->
                            if (!newVal) validate()
                        }
                    }

                    // first name field
                    jfxtextfield(labelFloat = true, promptText = "Prénom *") {
                        bind(controller.firstNameProperty)

                        prefHeight = 29.0; prefWidth = 186.0
                        layoutX = 298.0; layoutY = 101.0

                        focusColor   = Color.web("#00796b")
                        unFocusColor = Color.web("#dbdbdb")
                        font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                        isLabelFloat = true

                        validators.addAll(
                            RequiredFieldValidator("Champ requis")
                                .apply { icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING) },
                            RegexValidator("Nom invalide")
                                .apply {
                                    icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING)
                                    regexPattern = NAME_REGEX_PATTERN
                                }
                        )

                        focusedProperty().addListener { _, _, newVal ->
                            if (!newVal) validate()
                        }
                    }

                    // client field
                    jfxtextfield(labelFloat = true, promptText = "Client *") {
                        bind(controller.clientProperty)

                        prefHeight = 29.0; prefWidth = 186.0
                        layoutX = 298.0; layoutY = 174.0

                        focusColor   = Color.web("#00796b")
                        unFocusColor = Color.web("#dbdbdb")
                        font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                        isLabelFloat = true

                        validators.add(
                            RequiredFieldValidator("Champ requis")
                                .apply { icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING) },
                        )

                        focusedProperty().addListener { _, _, newVal ->
                            if (!newVal) validate()
                        }
                    }

                    // functions field
                    jfxtextfield(labelFloat = true, promptText = "Fonction(s) *") {
                        bind(controller.functionsProperty)

                        prefHeight = 29.0; prefWidth = 186.0
                        layoutX = 43.0; layoutY = 174.0

                        focusColor   = Color.web("#00796b")
                        unFocusColor = Color.web("#dbdbdb")
                        font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                        isLabelFloat = true

                        validators.add(
                            RequiredFieldValidator("Champ requis")
                                .apply { icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING) },
                        )

                        focusedProperty().addListener { _, _, newVal ->
                            if (!newVal) validate()
                        }
                    }

                    // date picker
                    jfxdatepicker() {
                        bind(controller.dateProperty)

                        prefHeight = 29.0; prefWidth = 441.0
                        layoutX = 43.0; layoutY = 247.0

                        promptText = "Date *"
                        defaultColor = Color.web("#00796b")
                        isOverLay = true
                        dialogParent = this@stackpane

                        validators.add(
                            RequiredFieldValidator("Champ requis")
                                .apply { icon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.WARNING) },
                        )

                        focusedProperty().addListener { _, _, newVal ->
                            if (!newVal) validate()
                        }
                    }

                    // note or observation area
                    jfxtextarea() {
                        bind(controller.noteProperty)

                        prefHeight = 70.0; prefWidth = 441.0
                        layoutX = 43.0; layoutY = 320.0

                        promptText = "Note ou autres..."
                        focusColor   = Color.web("#00796b")
                        unFocusColor = Color.web("#dbdbdb")
                        font = loadFont("/fonts/roboto-regular.ttf", 13.0)
                        isLabelFloat = true
                    }


                    // save button
                    jfxbutton("Sauvegarder", JFXButton.ButtonType.RAISED) {
                        prefHeight = 34.0; prefWidth = 186.0
                        layoutX = 299.0; layoutY = 474.0

                        style {
                            backgroundColor = multi(Color.web("#4DB6AC"))
                            textFill = Color.web("#FFFF")
                            font = loadFont("/fonts/roboto-regular.ttf", 11.0)!!
                        }

                        action {
                            if (!controller.canSave()) {
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
                                controller.save()
                            }
                        }
                    }

                    // reset
                    hyperlink ("Reset") {
                        prefHeight = 21.0; prefWidth = 38.0
                        layoutX = 447.0; layoutY = 513.0

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
                                            backgroundColor = multi(Color.web("#4CAF50"))
                                            textFill = Color.web("#FFFF")
                                            font = loadFont("/fonts/roboto-regular.ttf", 10.0)!!
                                        }

                                        action {
                                            controller.reset()
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
        }

        // shortcut : save (CTRL+S)
        shortcut("CTRL+S") {
            runAsync {
                controller.save()
            }
        }

        // shortcut : save (CTRL+P)
        shortcut("CTRL+P") {
            println("Printing")
        }
    }

    companion object {

        private const val NAME_REGEX_PATTERN = "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+\$"

    }

}