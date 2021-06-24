package org.mg.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.mg.algorithm.AStarAlgorithm
import org.mg.custom.ResizableCanvas
import tornadofx.*

import org.mg.controller.CanvasController
import kotlin.math.floor

class CanvasView: View("Canvas View") {
    override val root: BorderPane by fxml("/Canvas.fxml")
    private val controller: CanvasController by inject()
    private val optionsVbox: VBox by fxid()

    private val choices: ArrayList<String> = arrayListOf("Source", "Goal", "Pen", "Eraser")
    private val choicesList: ObservableList<String> = FXCollections.observableArrayList(choices)
    private val choiceBox: ChoiceBox<String> by fxid()

    private val sizeLabel: Label by fxid()
    private val delayLabel: Label by fxid()
    private val sizeSlider: Slider by fxid()
    private val delaySlider: Slider by fxid()

    private val clearButton: Button by fxid()
    private val runButton: Button by fxid()

    private var currentTool: String = "Pen"

    private val canvas: ResizableCanvas = ResizableCanvas()

    init {
        // Make canvas resizable
        root.center = canvas
        canvas.widthProperty().bind(root.widthProperty() - optionsVbox.widthProperty() - 35)
        canvas.heightProperty().bind(root.heightProperty() - 20)

        runButton.setOnAction { AStarAlgorithm.runAlgorithm(canvas) }
        clearButton.setOnAction { canvas.changeSize(canvas.size) }

        // Set choice box options
        choiceBox.value = "Pen"
        choiceBox.items = choicesList
        choiceBox.valueProperty().addListener { _, _, newValue -> currentTool = newValue }

        canvas.setOnMouseClicked { evt -> controller.selectRect(canvas, currentTool, evt.x, evt.y) }
        canvas.setOnMouseDragged { evt -> controller.selectRect(canvas, currentTool, evt.x, evt.y) }

        // Make sure sliders can only use increment values
        sizeSlider.valueProperty().addListener { _, _, newValue ->
            val roundedValue: Int = (floor(newValue.toDouble()/10.0) *10.0).toInt()
            sizeSlider.valueProperty().set(roundedValue+0.0)
            sizeLabel.text = "Size: ${roundedValue}x${roundedValue}"

            canvas.changeSize(roundedValue)
        }

        delaySlider.valueProperty().addListener { _, _, newValue ->
            val roundedValue: Double = floor(newValue.toDouble()/10.0) *10.0
            delaySlider.valueProperty().set(roundedValue)
            delayLabel.text = "Animation Delay: ${roundedValue.toInt()}ms"
        }
    }
}
