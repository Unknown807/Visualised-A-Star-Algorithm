package org.mg.custom

import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class ResizableCanvas: Canvas() {
    var size = 10

    var sourceNodeDrawn = false
    var sourceNode: GridNode? = null

    var goalNodeDrawn = false
    var goalNode: GridNode? = null

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
    }

    private fun draw() {
        gc.clearRect(0.0, 0.0, width, height)

        setFill("white")
        gc.fillRect(0.0, 0.0, width, height)

        for (col in 0 until size) {
            for (row in 0 until size) {
                val node: GridNode = getNode(row, col)

                if (node.obstacle) {
                    setFill("black")
                    fillRect(row, col)
                } else if (node.source) {
                    setFill("blue")
                    fillRect(row, col)
                } else if (node.goal) {
                    setFill("purple")
                    fillRect(row, col)
                } else {
                    setFill("white")
                }

                strokeRect(row, col)
            }
        }
    }

    fun changeSize(newSize: Int) {
        size = newSize
        nodeMatrix = Array(size) { row ->
            Array(size) { col -> GridNode(row, col) }
        }

        sourceNodeDrawn = false
        goalNodeDrawn = false
        draw()
    }

    fun fillRect(row: Int, col: Int) {
        gc.fillRect(
            col * widthPerRect + 1,
            row * heightPerRect + 1,
            widthPerRect - 1.5,
            heightPerRect - 1.5
        )
    }

    fun clearRect(row: Int, col: Int) {
        gc.clearRect(
            col * widthPerRect + 1,
            row * heightPerRect + 1,
            widthPerRect - 1.5,
            heightPerRect - 1.5
        )
    }

    fun strokeRect(row: Int, col: Int) {
        gc.strokeRect(
            col * widthPerRect,
            row * heightPerRect,
            widthPerRect,
            heightPerRect
        )
    }

    fun setFill(color: String) {
        when (color) {
            "white" -> gc.fill = Color.WHITE
            "black" -> gc.fill = Color.BLACK
            "blue"  -> gc.fill = Color.BLUE
            "purple" -> gc.fill = Color.PURPLE
            "red" -> gc.fill = Color.RED
            "green" -> gc.fill = Color.GREEN
        }
    }

    fun getNode(row: Int, col: Int): GridNode {
        return nodeMatrix[row][col]
    }

    override fun isResizable(): Boolean {
        return true
    }

    override fun prefWidth(nwidth: Double): Double {
        return nwidth
    }

    override fun prefHeight(nheight: Double): Double {
        return nheight
    }
}