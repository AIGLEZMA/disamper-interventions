package me.aiglez.disamper.interventions.controllers

import me.aiglez.disamper.interventions.models.Intervention
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import tornadofx.*

class DocumentController : Controller() {

    fun buildDocument(intervention: Intervention) {
        val document = PDDocument()
        val page = PDPage()

        document.addPage(page)

        val content = with(PDPageContentStream(document, page)) {
            beginText()
        }


    }

}