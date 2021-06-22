package org.mg.view

import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ChoiceBox
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.mg.ResizableCanvas
import tornadofx.*

import org.mg.controller.CanvasController

class CanvasView: View("Canvas View") {
    override val root: BorderPane by fxml("/Canvas.fxml")
    //private val controller: CanvasController by inject()
    private val optionsVbox: VBox by fxid()

    private val choices: ArrayList<String> = arrayListOf("Start", "Goal", "Pen", "Eraser")
    private val choicesList: ObservableList<String> = FXCollections.observableArrayList(choices)
    private val choiceBox: ChoiceBox<String> by fxid()

    private var currentTool: String = "Pen"

    private var canvas: ResizableCanvas = ResizableCanvas()

    init {
        // Make canvas resizable
        root.center = canvas
        canvas.widthProperty().bind(root.widthProperty() - optionsVbox.widthProperty() - 35)
        canvas.heightProperty().bind(root.heightProperty() - 20)

        // Set choice box options
        choiceBox.value = "Pen"
        choiceBox.items = choicesList

        choiceBox.valueProperty().addListener { _, _, newValue -> currentTool = newValue }
    }
}
