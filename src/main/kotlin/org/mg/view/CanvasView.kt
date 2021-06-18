package org.mg.view

import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.mg.ResizableCanvas
import tornadofx.*

import org.mg.controller.CanvasController

class CanvasView: View("Canvas View") {
    override val root: BorderPane by fxml("/Canvas.fxml")
    val optionsVbox: VBox by fxid()
    var canvas: ResizableCanvas = ResizableCanvas()

    init {
        root.center = canvas
        canvas.widthProperty().bind(root.widthProperty() - optionsVbox.widthProperty())
        canvas.heightProperty().bind(root.heightProperty())
    }

}
