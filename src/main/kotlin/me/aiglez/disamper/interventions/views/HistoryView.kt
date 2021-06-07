package me.aiglez.disamper.interventions.views

import me.aiglez.disamper.interventions.WIDTH
import tornadofx.*

class HistoryView : View("DISAMPER") {

    override val root = pane {
        style {
            backgroundColor = multi(c("#2c2f33"))
        }

        add(HeaderFragment::class)

        pane {
            prefHeight = 610.0; prefWidth = WIDTH
            layoutY = 90.0
        }
    }
}