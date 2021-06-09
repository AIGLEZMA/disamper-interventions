package me.aiglez.disamper.interventions

import javafx.stage.Stage
import me.aiglez.disamper.interventions.views.HistoryView
import tornadofx.*

const val WIDTH = 1000.0
const val HEIGHT = 700.0

class Main : App(HistoryView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = WIDTH
            minHeight = HEIGHT
            isResizable = false
            super.start(this)
        }
    }

    init {
        importStylesheet("/css/header.css")
        importStylesheet("/css/history.css")
    }
}

fun main() {
    launch<Main>()
}