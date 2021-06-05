package me.aiglez.disamper.interventions

import javafx.stage.Stage
import me.aiglez.disamper.interventions.views.MainView
import tornadofx.*

class Main : App(MainView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 900.0
            minHeight = 700.0
            isResizable = false
            super.start(this)
        }
    }

    init {
        importStylesheet("/css/custom.css")
    }

}

fun main() {
    launch<Main>()
}