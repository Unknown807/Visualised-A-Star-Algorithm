package org.mg.view

import javafx.scene.control.Label
import javafx.scene.control.TableView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import tornadofx.*

class Fxml : View("My View") {
    override val root: BorderPane by fxml("/Fxml.fxml")

    val rightTable: TableView<String> by fxid()
    val vbox by fxid<VBox>()

    init {
        vbox.children.add(Label("Label"))
    }
}
