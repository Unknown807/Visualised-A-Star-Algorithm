package org.mg.custom

class GridNode(var row: Int, var col: Int) {
    var HCost: Double = 0.0
    var GCost: Double = 0.0
    var FCost: Double = 0.0

    var obstacle: Boolean = false
    var source: Boolean = false
    var goal: Boolean = false
}