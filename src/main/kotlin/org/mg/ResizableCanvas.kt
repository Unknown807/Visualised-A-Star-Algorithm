package org.mg

import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class ResizableCanvas: Canvas() {
    var size = 10

    val widthPerRect: Double
        get() = width/size
    val heightPerRect: Double
        get() = height/size

    var nodeMatrix: Array<Array<GridNode>> = Array(size) { row ->
        Array(size) { col -> GridNode(row, col) }
    }

    private val gc: GraphicsContext = graphicsContext2D

    init {
        widthProperty().addListener { evt: Observable? -> draw() }
        heightProperty().addListener { evt: Observable? -> draw() }

//        for ( row in nodeMatrix.indices ) {
//            var rowStr: String = ""
//            for (col in nodeMatrix.indices) {
//                rowStr += "${nodeMatrix[row][col].col}, "
//            }
//            println(rowStr)
//        }
    }

    private fun draw() {
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

    fun getNode(row: Int, col: Int): GridNode {
        return nodeMatrix[row][col]
    }

    fun fillRect(row: Int, col: Int) {
        gc.fill = Color.BLACK
        gc.fillRect(
            col * widthPerRect,
            row * heightPerRect,
            widthPerRect,
            heightPerRect
        )
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