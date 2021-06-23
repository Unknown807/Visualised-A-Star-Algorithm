package org.mg.controller

import org.mg.GridNode
import org.mg.ResizableCanvas
import tornadofx.*

class CanvasController: Controller() {

    /* To get the upper left corner of the clicked rectangle you know that
     wherever the mouse clicked in the rectangle is some amount (double)
     of the widthPerRect and heightPerRect, so divide x and y by them
     respectively then convert to int to strip off the percentage past the
     upper left corner of the selected rectangle that actual click came
     from, then multiply each point by the widthPerRect and heightPerRect
     to get the coords for the upper left corner
     */
    fun selectRect(canvas: ResizableCanvas, currentTool: String, mouseX: Double, mouseY: Double) {
        val row: Int = (mouseY/canvas.heightPerRect).toInt()
        val col: Int = (mouseX/canvas.widthPerRect).toInt()

        when (currentTool) {
            "Pen" -> makeRectObstacle(canvas, row, col)
            "Eraser" -> makeRectFree(canvas, row, col)
        }

    }

    private fun makeRectObstacle(canvas: ResizableCanvas, row: Int, col: Int) {
        val node: GridNode = canvas.getNode(row, col)

        node.obstacle = true
        canvas.fillRect(row, col)
    }

    private fun makeRectFree(canvas: ResizableCanvas, row: Int, col: Int) {

    }

}