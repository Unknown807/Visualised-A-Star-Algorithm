package org.mg.algorithm

import org.mg.custom.GridNode
import org.mg.custom.ResizableCanvas
import kotlin.collections.ArrayList
import kotlin.math.*

class AStarAlgorithm {

    companion object {
        private val unevaluatedNodes: ArrayList<GridNode> = ArrayList()
        private val evaluatedNodes: ArrayList<GridNode> = ArrayList()

        private lateinit var sourceNode: GridNode
        private lateinit var goalNode: GridNode

        private var widthPerRect: Double = 0.0
        private var heightPerRect: Double = 0.0

        fun startAlgorithm(canvas: ResizableCanvas, delay: Long) {
            if (canvas.sourceNode === null || canvas.goalNode === null) {
                return // Include error alert here
            }

            setup(canvas)

            var found: Boolean = runAlgorithm(canvas)
            while (!found) {
                found = runAlgorithm(canvas)
                Thread.sleep(delay)
            }

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
            val node: GridNode = getMinFCostNode()

            unevaluatedNodes.remove(node)
            evaluatedNodes.add(node)
            canvas.setFill("red")
            canvas.fillRect(node.row, node.col)

            if (node === canvas.goalNode) {
                drawPath(canvas, node)
                return true // Found source node
            }

            val neighbours: ArrayList<GridNode> = getNodeNeighbours(canvas, node)

            for ( neighbourNode in neighbours ) {
                if (neighbourNode.obstacle ||evaluatedNodes.contains(neighbourNode)) {
                    continue
                }

                val nGCost: Int = node.GCost + calculateDistance(node, neighbourNode)

                if (neighbourNode.HCost == 0) {
                    neighbourNode.HCost = calculateDistance(neighbourNode, goalNode)
                }

                if (nGCost < neighbourNode.GCost || !unevaluatedNodes.contains(neighbourNode)) {
                    neighbourNode.GCost = nGCost
                    neighbourNode.FCost = neighbourNode.GCost + neighbourNode.HCost
                    neighbourNode.parentNode = node

                    if (!unevaluatedNodes.contains(neighbourNode)) {
                        unevaluatedNodes.add(neighbourNode)
                        canvas.setFill("green")
                        canvas.fillRect(neighbourNode.row, neighbourNode.col)
                    }
                }

            }

            return false
        }

        private fun drawPath(canvas: ResizableCanvas, node: GridNode?) {
            if (node === null) {
                return
            } else {
                canvas.setFill("blue")
                canvas.fillRect(node.row, node.col)
                return drawPath(canvas, node.parentNode)
            }
        }

        private fun getMinFCostNode(): GridNode {
            var minNode: GridNode = unevaluatedNodes[0]

            for ( i in 1 until unevaluatedNodes.size ) {
                val node = unevaluatedNodes[i]

                if (node.FCost < minNode.FCost) {
                    minNode = node
                } else if (node.FCost == minNode.FCost) {
                    if (node.HCost < minNode.HCost) {
                        minNode = node
                    }
                }
            }

            return minNode
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

        // Code here replaced my other two longer methods. Thanks to Sebastian Lague.
        private fun calculateDistance(node1: GridNode, node2: GridNode): Int {
            val dstX: Int = abs(node1.col - node2.col)
            val dstY: Int = abs(node1.row - node2.row)

            return if (dstX > dstY) {
                14 * dstY + 10 * (dstX - dstY)
            } else {
                14 * dstX + 10 * (dstY - dstX)
            }
        }

    }
}