package org.mg.controller

import org.mg.ResizableCanvas
import tornadofx.*

class CanvasController: Controller() {

    fun selectRect(canvas: ResizableCanvas, currentTool: String, mouseX: Double, mouseY: Double) {
        val widthPerRect: Double = canvas.width/canvas.size
        val heightPerRect: Double = canvas.height/canvas.size

        /* To get the upper left corner of the clicked rectangle you know that
        wherever the mouse clicked in the rectangle is some amount (double)
        of the widthPerRect and heightPerRect, so divide x and y by them
        respectively then convert to int to strip off the percentage past the
        upper left corner of the selected rectangle that actual click came
        from, then multiply each point by the widthPerRect and heightPerRect
        to get the coords for the upper left corner
         */

        val rectCornerX: Double = (mouseX/widthPerRect).toInt()*widthPerRect
        val rectCornerY: Double = (mouseY/heightPerRect).toInt()*heightPerRect

//        println("Row: ${(mouseY/heightPerRect).toInt()}, Col: ${(mouseX/widthPerRect).toInt()}")
//        println("Real Row: ${rectCornerY}, Real Col: ${rectCornerX}")

    }

}