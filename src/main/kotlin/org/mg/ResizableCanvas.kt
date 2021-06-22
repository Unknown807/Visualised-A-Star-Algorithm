package org.mg

import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

class ResizableCanvas: Canvas() {
    var size = 10
        set(value) { field=value }

    init {
        widthProperty().addListener { evt: Observable? -> draw() }
        heightProperty().addListener { evt: Observable? -> draw() }
    }

    private fun draw() {
        val gc: GraphicsContext = graphicsContext2D

        gc.clearRect(0.0, 0.0, width, height)

        for (row in 0..size) {
            for (col in 0..size) {
                gc.strokeRect(
                    col * width / size,
                    row * height / size,
                    width / size,
                    height / size
                )
            }
        }
    }

    override fun isResizable(): Boolean {
        return true
    }

    override fun prefWidth(nwidth: Double): Double {
        return width
    }

    override fun prefHeight(nheight: Double): Double {
        return height
    }
}