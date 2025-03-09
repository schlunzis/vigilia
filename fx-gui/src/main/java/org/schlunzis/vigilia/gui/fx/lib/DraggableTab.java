package org.schlunzis.vigilia.gui.fx.lib;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Setter;

import java.util.function.Consumer;

public class DraggableTab extends Tab {

    private static final Stage dragStage = new Stage();
    private static final Label dragText = new Label();

    static {
        dragStage.setOpacity(0.7);
        dragStage.initStyle(StageStyle.UNDECORATED);
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(dragText);
        AnchorPane.setTopAnchor(dragText, 5.0);
        AnchorPane.setLeftAnchor(dragText, 5.0);
        dragStage.setScene(new Scene(pane));
    }

    private final Label nameLabel;
    @Setter
    private Consumer<Point2D> onDraggedOutsideTabPane;

    public DraggableTab(String name) {
        nameLabel = new Label();
        setActualText(name);
        this.setGraphic(nameLabel);
        nameLabel.setOnMouseDragged(this::startDrag);
        nameLabel.setOnMouseReleased(this::endDrag);
    }

    private void startDrag(MouseEvent event) {
        System.out.println("startDrag");
        dragText.setText(nameLabel.getText());
        dragStage.setWidth(nameLabel.getWidth() + 10);
        dragStage.setHeight(nameLabel.getHeight() + 10);
        dragStage.setX(event.getScreenX());
        dragStage.setY(event.getScreenY());
        dragStage.show();
    }

    private void endDrag(MouseEvent event) {
        System.out.println("endDrag");
        dragStage.close();
        // check if the mouse is still in the tab header
        final double xInScene = event.getSceneX();
        final double yInScene = event.getSceneY();
        MainView tabPane = (MainView) getTabPane();
        if (tabPane.isHeaderIntersects(xInScene, yInScene)) {
            return;
        }

        // otherwise, notify the listener that the tab was dragged outside the tab pane
        final double xOnScreen = event.getScreenX();
        final double yOnScreen = event.getScreenY();
        onDraggedOutsideTabPane.accept(new Point2D(xOnScreen, yOnScreen));
        event.consume();
    }

    public void setActualText(String text) {
        nameLabel.setText(text);
    }

}
