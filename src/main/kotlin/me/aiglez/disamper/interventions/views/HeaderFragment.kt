package me.aiglez.disamper.interventions.views

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.layout.StackPane
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
                backgroundColor = multi(c("#2c2f33"))
            }
            effect = DropShadow(10.0, c("#202225"))

            // text
            text("DISAMPER") {
                fill = c("#FFFFFF")
                font = loadFont("/fonts/roboto-medium.ttf", 29)

                stackpaneConstraints {
                    alignment = Pos.CENTER_LEFT
                    marginLeft = 20.0
                }
            }

            // button
            jfxbutton("AJOUTER", JFXButton.ButtonType.RAISED) {
                ripplerFill = c("#23272a")
                textFill = c("#FFFFFF")
                font = Font.font("Segoe UI Semibold", 15.0)

                graphic = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS).apply {
                    fill = c("#FFFFFF")
                }

                stackpaneConstraints {
                    alignment = Pos.CENTER_RIGHT
                    marginRight = 20.0
                }
            }

            // icon
            add(
                FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.BOLT, "50").apply {
                    fill = c("#7289da")
                    effect = DropShadow(10.0, c("#202225"))
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