package me.aiglez.disamper.interventions

import me.aiglez.disamper.interventions.mvc.MainView
import tornadofx.*

class Main : App(MainView::class) {

    init {
        importStylesheet("/css/our.css")
    }

}

fun main() {
    launch<Main>()
}