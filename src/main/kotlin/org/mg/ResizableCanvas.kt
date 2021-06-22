package org.mg

import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class ResizableCanvas: Canvas() {
    var size = 10

    init {
        widthProperty().addListener { evt: Observable? -> draw() }
        heightProperty().addListener { evt: Observable? -> draw() }
    }

    private fun draw() {
        val gc: GraphicsContext = graphicsContext2D
        val widthPerRect: Double = width / size
        val heightPerRect: Double = height / size

        gc.clearRect(0.0, 0.0, width, height)

        gc.fill = Color.WHITE;
        gc.fillRect(0.0, 0.0, width, height)

        for (col in 0..size) {
            for (row in 0..size) {
                gc.strokeRect(
                    col * widthPerRect,
                    row * heightPerRect,
                    widthPerRect,
                    heightPerRect
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