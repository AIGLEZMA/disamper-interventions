package me.aiglez.disamper.interventions.utils

import com.jfoenix.controls.*
import com.jfoenix.controls.JFXButton.ButtonType.FLAT
import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.control.ToolBar
import javafx.util.StringConverter
import tornadofx.*
import java.time.LocalDate
import kotlin.reflect.KClass

// source: https://github.com/bkenn/KFoenix/blob/master/src/main/kotlin/kfoenix/Controls.kt

/**
 * Spinner
 */
fun EventTarget.jfxspinner(property: ObservableValue<Boolean>? = null, op: JFXSpinner.() -> Unit = {}): JFXSpinner {
    val spinner = JFXSpinner()
    if(property != null) spinner.visibleProperty().bind(property)
    return opcr(this, spinner, op)
}

/**
 * Buttons
 */
fun ButtonBar.jfxbutton(value: String? = null,
                        btnType: JFXButton.ButtonType = FLAT,
                        graphic: Node? = null,
                        type: ButtonBar.ButtonData? = null,
                        op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    if(type != null)    ButtonBar.setButtonData(button, type)
    buttons.add(button)
    return button.also(op)
}

fun ButtonBar.jfxbutton(property :ObservableValue<String>,
                        btnType: JFXButton.ButtonType = FLAT,
                        graphic: Node? = null,
                        type: ButtonBar.ButtonData? = null,
                        op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, type = type, op = op).apply { bind(property) }

fun EventTarget.jfxbutton(value :String? = null,
                          btnType: JFXButton.ButtonType = FLAT,
                          graphic: Node? = null,
                          op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    return opcr(this, button, op)
}

fun EventTarget.jfxbutton(property :ObservableValue<String>,
                          btnType: JFXButton.ButtonType = FLAT,
                          graphic: Node? = null,
                          op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, op = op).apply { bind(property) }

fun ToolBar.jfxbutton(value: String? = null,
                      btnType: JFXButton.ButtonType = FLAT,
                      graphic: Node? = null, op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    add(button)
    return button.also(op)
}

fun ToolBar.jfxbutton(property :ObservableValue<String>,
                      btnType: JFXButton.ButtonType = FLAT,
                      graphic: Node? = null,
                      op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, op = op).apply { bind(property) }

/**
 * DatePicker
 */
fun EventTarget.jfxdatepicker(op: JFXDatePicker.() -> Unit = {}) : JFXDatePicker = opcr(this, JFXDatePicker(), op)

fun EventTarget.jfxdatepicker(property: Property<LocalDate>, op: JFXDatePicker.() -> Unit = {}): JFXDatePicker
        = jfxdatepicker(op).apply { bind(property) }

/**
 * Popup
 */
fun EventTarget.jfxpopup(op: JFXPopup.() -> Unit = {}) = JFXPopup().apply(op)

/**
 * Textfield
 */
fun EventTarget.jfxtextfield(value: String? = null,
                             promptText: String? = null,
                             labelFloat: Boolean = false,
                             op: JFXTextField.() -> Unit = {}): JFXTextField {
    val textfield = JFXTextField(value)
    textfield.isLabelFloat = labelFloat
    if(promptText != null) textfield.promptText = promptText
    return opcr(this,textfield,op)
}

fun EventTarget.jfxtextfield(property: ObservableValue<String>,
                             promptText: String? = null,
                             labelFloat: Boolean = false,
                             op: JFXTextField.() -> Unit = {}) : JFXTextField
        = jfxtextfield(promptText = promptText, labelFloat = labelFloat, op = op).apply { bind(property) }

/**
 * TextArea
 */
fun EventTarget.jfxtextarea(value: String? = null, op: JFXTextArea.() -> Unit = {}): JFXTextArea
        = opcr(this, JFXTextArea(value), op)

fun EventTarget.jfxtextarea(property: ObservableValue<String>, op: JFXTextArea.() -> Unit = {}): JFXTextArea
        = jfxtextarea(op=op).apply { bind(property) }

fun <T> EventTarget.jfxtextarea(property: Property<T>,
                                converter: StringConverter<T>,
                                op: JFXTextArea.() -> Unit = {}) : JFXTextArea = jfxtextarea().apply {
    textProperty().bindBidirectional(property, converter)
    ViewModel.register(textProperty(), property)
    op(this)
}


/**
 * @param uiComponent View or Fragment to find.
 * @param findScope the scope for the uiComponent to use
 */
class ReplaceContentEvent<T: UIComponent>(val uiComponent: KClass<T>, val findScope: Scope = DefaultScope): FXEvent()