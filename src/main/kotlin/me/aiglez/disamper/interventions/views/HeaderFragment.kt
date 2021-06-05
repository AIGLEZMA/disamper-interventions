package me.aiglez.disamper.interventions.views

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import tornadofx.*

class HeaderFragment : Fragment() {

    override val root = stackpane {
        alignment = Pos.CENTER_LEFT
        prefHeight = 70.0; prefWidth = 900.0

        rectangle(width = 900.0, height = 69.0) {
            alignment = Pos.TOP_CENTER
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
}