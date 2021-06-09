package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import me.aiglez.disamper.interventions.WIDTH
import me.aiglez.disamper.interventions.utils.jfxbutton
import tornadofx.*

class HeaderFragment : Fragment() {

    override val root: StackPane =
        stackpane {
            prefHeight = 90.0; prefWidth = WIDTH
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
}