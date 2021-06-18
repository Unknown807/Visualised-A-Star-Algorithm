package org.mg

import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

class ResizableCanvas: Canvas() {

    init {
        widthProperty().addListener { evt: Observable? -> draw() }
        heightProperty().addListener { evt: Observable? -> draw() }
    }

    private fun draw() {
        val gc: GraphicsContext = graphicsContext2D;

        gc.clearRect(0.0,0.0,width,height)

        gc.strokeLine(0.0,0.0,width,height)
        gc.strokeLine(0.0,height,width,0.0)
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