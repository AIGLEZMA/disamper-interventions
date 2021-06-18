package me.aiglez.disamper.interventions.controllers

import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.itextpdf.layout.property.VerticalAlignment
import me.aiglez.disamper.interventions.folder
import me.aiglez.disamper.interventions.models.Intervention
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.printing.PDFPageable
import tornadofx.*
import java.awt.print.PrinterException
import java.awt.print.PrinterJob
import java.io.File
import javax.imageio.ImageIO
import javax.print.PrintServiceLookup
import kotlin.system.measureTimeMillis

class DocumentController : Controller() {

    fun save(intervention: Intervention, dir: File) {
        buildDocument(intervention, File(dir, "intervention_${intervention.id.value}.pdf"))
    }

    fun print(intervention: Intervention) {
        val file = File(folder, ".temp_${intervention.id.value}.pdf")
        buildDocument(intervention, file)

        println("Printing a file")
        val ms = measureTimeMillis {
            val document = PDDocument.load(file)

            val printService = PrintServiceLookup.lookupDefaultPrintService() ?: throw PrinterException()
            PrinterJob.getPrinterJob().apply {
                setPageable(PDFPageable(document))
                setPrintService(printService)
                jobName = "Intervention #${intervention.id.value}"

                print()
            }
        }

        println("Printing took $ms ms, deleting the temp file")
        file.delete()
    }

    private fun buildDocument(intervention: Intervention, file: File) {
        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        // FONTS
        val roboto = PdfFontFactory.createFont(robotoFP, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED)

        val robotoMedium = PdfFontFactory.createFont(robotoMediumFP, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED)

        val robotoLight = PdfFontFactory.createFont(robotoLightFP, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED)

        // HEADER
        document.add(
            Table(UnitValue.createPercentArray(floatArrayOf(1f, 2f))).apply {
                setWidth(375f)
                addCell(
                    Cell()
                        .apply {
                            val imageIs = javaClass.getResourceAsStream("/img/logo.jpg")!!
                            val image = Image(ImageDataFactory.create(ImageIO.read(imageIs), null)).apply {
                                scaleToFit(80f, 85f)
                                imageIs.close()
                            }

                            add(image)
                            setBorder(null)
                        }
                )

                addCell(
                    Cell()
                        .apply {
                            val title = Paragraph("DISAMPER")
                                .apply {
                                    setTextAlignment(TextAlignment.CENTER)
                                    setFontSize(42f)
                                    setFont(robotoMedium)
                                }
                            add(title)
                            setVerticalAlignment(VerticalAlignment.MIDDLE)
                            setBorder(Border.NO_BORDER)

                            val subject = Paragraph("Fiche d'intervention #${intervention.id}")
                                .apply {
                                    setTextAlignment(TextAlignment.CENTER)
                                    setFontSize(24f)
                                    setFont(roboto)
                                    setMarginBottom(100f)
                                }
                            add(subject)
                        }
                )
            }
        )

        // FORMS
        val intervenant = Table(3, true)
        document.add(intervenant)

        intervenant.apply {
            setBorder(SolidBorder(1f))

            addCell(createNewCell("NOM:", robotoLight, 17f, TextAlignment.LEFT))
            addCell(createNewCell("PRÉNOM:", robotoLight, 17f, TextAlignment.CENTER))
            addCell(createNewCell("FONCTION(s):", robotoLight, 17f, TextAlignment.RIGHT))

            addCell(createNewCell(intervention.lastName, roboto, 14f, TextAlignment.LEFT))
            addCell(createNewCell(intervention.firstName, roboto, 14f, TextAlignment.CENTER))
            addCell(createNewCell(intervention.functions, roboto, 14f, TextAlignment.RIGHT))

            complete()
        }

        val client = Table(2, true)
        document.add(client)

        client.apply {
            setBorder(SolidBorder(1f))

            addCell(createNewCell("CLIENT:", robotoLight, 17f, TextAlignment.LEFT))
            addCell(createNewCell("DATE:", robotoLight, 17f, TextAlignment.RIGHT))

            addCell(createNewCell(intervention.client, roboto, 14f, TextAlignment.LEFT))
            addCell(createNewCell(intervention.date.toString(), roboto, 14f, TextAlignment.RIGHT))

            setMarginTop(50f)

            complete()
        }

        document.add(
            Table(1, true).apply {
                addCell(createNewCell("NOTE OU AUTRES:", robotoLight, 17f, TextAlignment.CENTER))
                if (!intervention.note.isNullOrEmpty()) {
                    addCell(createNewCell(intervention.note!!, roboto, 14f, TextAlignment.LEFT))
                }

                setMarginTop(90f)
            }
        )

        document.close()
    }

    private fun createNewCell(value: String, font: PdfFont, size: Float, alignement: TextAlignment): Cell {
        return Cell().apply {
            add(Paragraph(value).apply {
                    setFont(font)
                    setFontSize(size)
                }
            )
            setBorder(Border.NO_BORDER)
            setTextAlignment(alignement)
        }
    }

    companion object {

        val robotoFP = FontProgramFactory.createFont("src/main/resources/fonts/roboto-regular.ttf")

        val robotoMediumFP = FontProgramFactory.createFont("src/main/resources/fonts/roboto-medium.ttf")

        val robotoLightFP = FontProgramFactory.createFont("src/main/resources/fonts/roboto-light.ttf")

    }

}
