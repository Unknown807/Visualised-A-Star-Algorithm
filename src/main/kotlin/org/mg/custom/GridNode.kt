package org.mg.custom

class GridNode(var row: Int, var col: Int) {
    var parentNode: GridNode? = null

    var HCost: Int = 0
    var GCost: Int = 0
    var FCost: Int = 0
        get() = GCost + HCost

    var obstacle: Boolean = false
    var source: Boolean = false
    var goal: Boolean = false
}