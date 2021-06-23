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
            "Source" -> makeRectSource(canvas, row, col)
            "Goal" -> makeRectGoal(canvas, row, col)
        }

    }

    private fun makeRectSource(canvas: ResizableCanvas, row: Int, col: Int) {
        val node: GridNode = canvas.getNode(row, col)

        if (!node.obstacle && !node.source && !node.goal && !canvas.sourceNodeDrawn) {
            node.source = true
            canvas.sourceNodeDrawn = true

            canvas.setFill("blue")
            canvas.fillRect(row, col)
        }
    }

    private fun makeRectGoal(canvas: ResizableCanvas, row: Int, col: Int) {
        val node: GridNode = canvas.getNode(row, col)

        if (!node.obstacle && !node.source && !node.goal && !canvas.goalNodeDrawn) {
            node.goal = true
            canvas.goalNodeDrawn = true

            canvas.setFill("purple")
            canvas.fillRect(row, col)
        }
    }

    private fun makeRectObstacle(canvas: ResizableCanvas, row: Int, col: Int) {
        val node: GridNode = canvas.getNode(row, col)

        if (!node.obstacle && !node.source && !node.goal ) {
            node.obstacle = true
            canvas.setFill("black")
            canvas.fillRect(row, col)
        }
    }

    private fun makeRectFree(canvas: ResizableCanvas, row: Int, col: Int) {
        val node: GridNode = canvas.getNode(row, col)

        if (node.obstacle || node.source || node.goal) {

            if (node.source) {
                canvas.sourceNodeDrawn = false
            } else if (node.goal) {
                canvas.goalNodeDrawn = false
            }

            node.obstacle = false
            node.goal = false
            node.source = false

            canvas.setFill("white")

            // So that the user can't draw lots of rects again and again
            canvas.clearRect(row, col)
            canvas.strokeRect(row, col)
            canvas.fillRect(row, col)

        }
    }

}