package me.aiglez.disamper.interventions

import javafx.scene.image.Image
import javafx.stage.Stage
import me.aiglez.disamper.interventions.views.HistoryView
import tornadofx.*
import java.io.File
import javax.swing.filechooser.FileSystemView

class Main : App(HistoryView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1000.0
            minHeight = 700.0
            isResizable = false
            super.start(this)
        }
        setStageIcon(Image(this.javaClass.getResourceAsStream("/img/icon.jpg")))
    }

    init {
        importStylesheet("/css/header.css")
        importStylesheet("/css/history.css")
    }
}

val folder = File(File(FileSystemView.getFileSystemView().defaultDirectory, "Disamper"), "Interventions")

fun main() {
    launch<Main>()
}