package me.aiglez.disamper.interventions.views

import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.system.measureTimeMillis

class MainView: View("Disamper") {

    override val root = vbox(alignment = Pos.TOP_CENTER) {
        add(HeaderFragment::class)
        pane {
            prefHeight = 630.0

            style {
                backgroundColor = multi(c("#FAFAFA"))
            }

            vbox(alignment = Pos.TOP_CENTER) {
                prefHeight = 500.0; prefWidth = 850.0
                layoutX = 25.0; layoutY = 65.0

                style {
                    backgroundColor = multi(Color.WHITE)
                }
                effect = DropShadow(300.0, Color.web("#e8e8e8"))

                label("Historique des interventions") {
                    textFill = c("#00897B")
                    val ms = measureTimeMillis {
                        font = loadFont("/fonts/roboto-medium.ttf", 25.0)
                    }
                    lineSpacing = 0.15

                    paddingTop = 30

                    println("Loading roboto font took $ms")
                }

                // ugh
                add(HistoryFragment::class)
            }
        }
    }

}