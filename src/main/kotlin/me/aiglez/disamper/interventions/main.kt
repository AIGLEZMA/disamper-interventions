package me.aiglez.disamper.interventions

import com.sun.javafx.PlatformUtil
import javafx.scene.image.Image
import javafx.stage.Stage
import me.aiglez.disamper.interventions.views.HistoryView
import tornadofx.*
import java.nio.file.Path
import java.nio.file.Paths

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

val cacheDir: Path by lazy {
    when {
        PlatformUtil.isWinVistaOrLater() -> {
            Paths.get(System.getenv("ProgramData"), "Disamper", "Interventions")
        }
        PlatformUtil.isWindows() -> {
            Paths.get(System.getenv("ALLUSERSPROFILE"), "Disamper", "Interventions")
        }
        PlatformUtil.isLinux() -> {
            Paths.get("Library", "Application Support", "Disamper", "Interventions")
        }
        else -> {
            Paths.get("usr", "local", "share", "Disamper", "Interventions")
        }
    }
}

fun main() {
    launch<Main>()
}