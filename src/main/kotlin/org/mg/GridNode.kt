package org.mg

class GridNode(var row: Int, var col: Int) {
    private var HCost: Double = 0.0
    private var GCost: Double = 0.0
    private var FCost: Double = 0.0

    var obstacle: Boolean = false
    var source: Boolean = false
    var goal: Boolean = false
}