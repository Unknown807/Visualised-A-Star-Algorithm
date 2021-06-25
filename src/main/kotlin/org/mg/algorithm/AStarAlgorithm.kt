package org.mg.algorithm

import org.mg.custom.GridNode
import org.mg.custom.ResizableCanvas
import kotlin.math.pow
import kotlin.math.sqrt

class AStarAlgorithm {

    companion object {
        private val unevaluatedNodes: ArrayList<GridNode> = ArrayList()
        private val evaluatedNodes: ArrayList<GridNode> = ArrayList()

        private lateinit var sourceNode: GridNode
        private lateinit var goalNode: GridNode

        private var widthPerRect: Double = 0.0
        private var heightPerRect: Double = 0.0

        fun startAlgorithm(canvas: ResizableCanvas) {
            if (canvas.sourceNode === null || canvas.goalNode === null) {
                return // Include error alert here
            }

            setup(canvas)

            var found: Boolean = runAlgorithm(canvas)
            while (!found) {
                found = runAlgorithm(canvas)
            }

            println(found)

        }

        private fun setup(canvas: ResizableCanvas) {
            widthPerRect = canvas.widthPerRect
            heightPerRect = canvas.heightPerRect

            sourceNode = canvas.sourceNode!!
            goalNode = canvas.goalNode!!

            sourceNode.HCost = calculateDistance(sourceNode, goalNode)
            sourceNode.FCost = sourceNode.GCost + sourceNode.HCost

            unevaluatedNodes.add(sourceNode)
        }

        private fun runAlgorithm(canvas: ResizableCanvas): Boolean {
            val node: GridNode = unevaluatedNodes.minBy { it -> it.FCost }!!
            unevaluatedNodes.remove(node)
            evaluatedNodes.add(node)

            if (node === canvas.sourceNode) {
                return true// Found source node
            }

            val neighbours: ArrayList<GridNode> = getNodeNeighbours(canvas, node)

            for ( neighbourNode in neighbours ) {
                if (neighbourNode.obstacle || evaluatedNodes.contains(neighbourNode)) {
                    continue
                }

                val nGCost: Double = calculateGCost(node, calculateDistance(neighbourNode, node))

                if (neighbourNode.HCost == 0.0) {
                    neighbourNode.HCost = calculateDistance(neighbourNode, goalNode)
                }

                if (nGCost < neighbourNode.GCost || !unevaluatedNodes.contains(neighbourNode)) {
                    neighbourNode.GCost = nGCost
                    neighbourNode.FCost = neighbourNode.GCost + neighbourNode.HCost
                    neighbourNode.parentNode = node

                    if (!unevaluatedNodes.contains(neighbourNode)) {
                        unevaluatedNodes.add(neighbourNode)
                    }
                }

            }

            return false
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

        private fun calculateGCost(node: GridNode?, distance: Double): Double {
            return if (node === null) {
                distance
            } else {
                calculateGCost(node.parentNode, distance+node.GCost)
            }
        }

        private fun calculateDistance(node1: GridNode, node2: GridNode): Double {
            val node1CornerX: Double = node1.col * widthPerRect
            val node1CornerY: Double = node1.row * heightPerRect

            val node2CornerX: Double = node2.col * widthPerRect
            val node2CornerY: Double = node2.row * heightPerRect

            return sqrt(
                (node2CornerX - node1CornerX).pow(2) + (node2CornerY - node1CornerY).pow(2)
            )
        }

    }
}