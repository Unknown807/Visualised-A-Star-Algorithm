package org.mg.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.mg.algorithm.AStarAlgorithm
import org.mg.custom.ResizableCanvas
import tornadofx.*

import org.mg.controller.CanvasController
import kotlin.concurrent.thread
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

    private val statusLabel: Label by fxid()

    private var currentTool: String = "Pen"
    private var algorithmRunning: Boolean = false

    private val canvas: ResizableCanvas = ResizableCanvas()

    init {
        // Make canvas resizable
        root.center = canvas
        canvas.widthProperty().bind(root.widthProperty() - optionsVbox.widthProperty() - 35)
        canvas.heightProperty().bind(root.heightProperty() - 20)

        runButton.setOnAction {
            if (!algorithmRunning) {
                algorithmRunning = true
                thread {
                    AStarAlgorithm.startAlgorithm(canvas, statusLabel, delaySlider.value.toLong())
                    algorithmRunning = false
                }
            }
        }
        clearButton.setOnAction {
            if (!algorithmRunning) {
                canvas.changeSize(canvas.size)
            }
        }

        // Set choice box options
        choiceBox.value = "Pen"
        choiceBox.items = choicesList
        choiceBox.valueProperty().addListener { _, _, newValue -> currentTool = newValue }

        canvas.setOnMouseClicked { evt ->
            if (!algorithmRunning) {
                controller.selectRect(canvas, currentTool, evt.x, evt.y)
            }
        }

        canvas.setOnMouseDragged { evt ->
            if (!algorithmRunning) {
                controller.selectRect(canvas, currentTool, evt.x, evt.y)
            }
        }

        // Make sure sliders can only use increment values
        sizeSlider.valueProperty().addListener { _, _, newValue ->
            if (!algorithmRunning) {
                val roundedValue: Int = (floor(newValue.toDouble() / 10.0) * 10.0).toInt()
                sizeSlider.valueProperty().set(roundedValue + 0.0)
                sizeLabel.text = "Size: ${roundedValue}x${roundedValue}"

                canvas.changeSize(roundedValue)
            }
        }

        delaySlider.valueProperty().addListener { _, _, newValue ->
            if (!algorithmRunning) {
                val roundedValue: Double = floor(newValue.toDouble() / 10.0) * 10.0
                delaySlider.valueProperty().set(roundedValue)
                delayLabel.text = "Animation Delay: ${roundedValue.toInt()}ms"
            }
        }
    }
}
