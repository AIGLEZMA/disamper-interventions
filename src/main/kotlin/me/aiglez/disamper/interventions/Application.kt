package me.aiglez.disamper.interventions

import javafx.stage.Stage
import me.aiglez.disamper.interventions.views.HistoryView
import tornadofx.*

class Application : App(HistoryView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1000.0
            minHeight = 700.0
            isResizable = false
            super.start(this)
        }
    }

    init {
        importStylesheet("/css/header.css")
        importStylesheet("/css/history.css")
    }
}