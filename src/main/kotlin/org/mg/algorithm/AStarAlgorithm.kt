package org.mg.algorithm

import org.mg.custom.GridNode
import org.mg.custom.ResizableCanvas
import kotlin.math.pow
import kotlin.math.sqrt

class AStarAlgorithm {
    companion object {
        private val unevaluatedNodes: ArrayList<GridNode> = ArrayList()
        private val evaluatedNodes: ArrayList<GridNode> = ArrayList()

        private var sourceNodeCornerX: Double = 0.0
        private var sourceNodeCornerY: Double = 0.0
        private var goalNodeCornerX: Double = 0.0
        private var goalNodeCornerY: Double = 0.0

        private var widthPerRect: Double = 0.0
        private var heightPerRect: Double = 0.0

        fun startAlgorithm(canvas: ResizableCanvas) {
            if (canvas.sourceNode === null || canvas.goalNode === null) {
                return // Include error alert here
            }

            setup(canvas)

            while (true) {
                runAlgorithm(canvas)
            }

        }

        private fun setup(canvas: ResizableCanvas) {
            widthPerRect = canvas.widthPerRect
            heightPerRect = canvas.heightPerRect

            sourceNodeCornerX = canvas.sourceNode!!.col * widthPerRect
            sourceNodeCornerY = canvas.sourceNode!!.row * heightPerRect

            goalNodeCornerX = canvas.goalNode!!.col * widthPerRect
            goalNodeCornerY = canvas.goalNode!!.row * heightPerRect

            unevaluatedNodes.add(canvas.sourceNode!!)
        }

        private fun runAlgorithm(canvas: ResizableCanvas) {

        }

//            for ( row in 0 until canvas.size) {
//                for ( col in 0 until canvas.size ) {
//                    val node: GridNode = canvas.getNode(row, col)
//                    val nodeCornerX: Double = node.col * widthPerRect
//                    val nodeCornerY: Double = node.row * heightPerRect
//
//                    // calculate node G cost
//                    node.GCost = calculateDistance(
//                        nodeCornerX, nodeCornerY,
//                        sourceNodeCornerX, sourceNodeCornerY
//                    )
//
//                    node.HCost = calculateDistance(
//                        nodeCornerX, nodeCornerY,
//                        goalNodeCornerX, goalNodeCornerY
//                    )
//
//                    node.FCost = node.GCost + node.HCost
//                }
//            }

        private fun calculateDistance(node1CornerX: Double, node1CornerY: Double, node2CornerX: Double, node2CornerY: Double): Double {
            return sqrt(
                (node2CornerX - node1CornerX).pow(2) + (node2CornerY - node1CornerY).pow(2)
            )
        }

    }
}