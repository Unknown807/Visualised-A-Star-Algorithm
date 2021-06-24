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

            //while (true) {
            runAlgorithm(canvas)
            //}

        }

        private fun setup(canvas: ResizableCanvas) {
            widthPerRect = canvas.widthPerRect
            heightPerRect = canvas.heightPerRect

            sourceNodeCornerX = canvas.sourceNode!!.col * widthPerRect
            sourceNodeCornerY = canvas.sourceNode!!.row * heightPerRect

            goalNodeCornerX = canvas.goalNode!!.col * widthPerRect
            goalNodeCornerY = canvas.goalNode!!.row * heightPerRect

            canvas.sourceNode!!.GCost = calculateGCost(canvas.sourceNode!!)
            canvas.sourceNode!!.HCost = calculateHCost(canvas.sourceNode!!)
            canvas.sourceNode!!.FCost = canvas.sourceNode!!.GCost + canvas.sourceNode!!.HCost

            unevaluatedNodes.add(canvas.sourceNode!!)
        }

        private fun runAlgorithm(canvas: ResizableCanvas) {
            val node: GridNode = unevaluatedNodes.minBy { it -> it.FCost }!!
            unevaluatedNodes.remove(node)
            evaluatedNodes.add(node)

            if (node === canvas.sourceNode) {
                return // Found source node
            }

            for



        }

        private fun getNodeNeighbours(canvas: ResizableCanvas, node: GridNode): ArrayList<GridNode> {
            val neighbours: ArrayList<GridNode> = ArrayList()
            val neighbourPositions: Array<Pair<Int, Int>> = arrayOf(
                Pair(-1, -1), // Top left corner
                Pair(0, -1), // Top
                Pair(1, -1), // Top right corner
                Pair(1, 0), // Right
                Pair(1, 1), // Bottom right corner
                Pair(0, 1), // Bottom
                Pair(-1, 1), // Bottom left corner
                Pair(-1, 0) // Left
            )

            for ( i in 0 until 8) {
                val pos: Pair<Int, Int> = neighbourPositions[i]
                val nCol: Int = pos.first+node.col
                val nRow: Int = pos.second+node.row

                if (nRow in 0 until canvas.size && nCol in 0 until canvas.size) {
                    val neighbour: GridNode = canvas.getNode(nRow, nCol)
                    neighbours.add(neighbour)
                }
            }

            return neighbours
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

        // Distance of node from source node
        private fun calculateGCost(node: GridNode): Double {
            val nodeCornerX: Double = node.col * widthPerRect
            val nodeCornerY: Double = node.row * heightPerRect
            return calculateDistance(
                nodeCornerX, nodeCornerY,
                sourceNodeCornerX, sourceNodeCornerY
            )
        }

        // Distance of node from goal node
        private fun calculateHCost(node: GridNode): Double {
            val nodeCornerX: Double = node.col * widthPerRect
            val nodeCornerY: Double = node.row * heightPerRect
            return calculateDistance(
                nodeCornerX, nodeCornerY,
                goalNodeCornerX, goalNodeCornerY
            )
        }

        private fun calculateDistance(node1CornerX: Double, node1CornerY: Double, node2CornerX: Double, node2CornerY: Double): Double {
            return sqrt(
                (node2CornerX - node1CornerX).pow(2) + (node2CornerY - node1CornerY).pow(2)
            )
        }

    }
}