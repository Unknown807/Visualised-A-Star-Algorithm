package org.mg.algorithm

import javafx.scene.control.Label
import javafx.scene.paint.Color
import org.mg.custom.GridNode
import org.mg.custom.ResizableCanvas
import tornadofx.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class AStarAlgorithm {

    companion object {
        private val unevaluatedNodes: ArrayList<GridNode> = ArrayList()
        private val evaluatedNodes: ArrayList<GridNode> = ArrayList()

        private lateinit var sourceNode: GridNode
        private lateinit var goalNode: GridNode

        fun startAlgorithm(canvas: ResizableCanvas, statusLabel: Label, delay: Long) {
            if (canvas.sourceNode === null || canvas.goalNode === null) {
                runLater {
                    statusLabel.textFill = Color.RED
                    statusLabel.text = "Source and goal node needed!"
                }
                return
            }

            setup(canvas)

            var found: Boolean = runAlgorithm(canvas, statusLabel)
            while (!found) {
                Thread.sleep(delay)
                found = runAlgorithm(canvas, statusLabel)
            }

        }

        private fun setup(canvas: ResizableCanvas) {
            sourceNode = canvas.sourceNode!!
            goalNode = canvas.goalNode!!

            sourceNode.HCost = calculateDistance(sourceNode, goalNode)

            unevaluatedNodes.add(sourceNode)
        }

        private fun runAlgorithm(canvas: ResizableCanvas, statusLabel: Label): Boolean {
            if (unevaluatedNodes.size < 1) {
                runLater {
                    statusLabel.textFill = Color.RED
                    statusLabel.text = "No possible paths found"
                }
                resetAlgorithm(canvas)
                return true
            }

            val node: GridNode = getMinFCostNode()

            unevaluatedNodes.remove(node)
            evaluatedNodes.add(node)
            canvas.setFill("red")
            canvas.fillRect(node.row, node.col)

            if (node === canvas.goalNode) {
                canvas.setFill("blue")
                drawPath(canvas, node)

                runLater {
                    statusLabel.textFill = Color.GREEN
                    statusLabel.text = "Algorithm Finished Successfully"
                }

                // Give delay for user to view screen, then reset board
                Thread.sleep(10_000)
                resetAlgorithm(canvas)
                runLater {
                    statusLabel.textFill = Color.BLACK
                    statusLabel.text = "Ready..."
                }

                return true // Found goal node
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

        private fun resetAlgorithm(canvas: ResizableCanvas) {
            unevaluatedNodes.clear()
            evaluatedNodes.clear()
            canvas.draw()
        }

        private fun drawPath(canvas: ResizableCanvas, node: GridNode?) {
            if (node === null) {
                return
            } else {
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