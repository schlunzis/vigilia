package org.schlunzis.vigilia.gui.fx.lib;

import javafx.stage.Stage;

public class DefaultStageFactory implements StageFactory {

    @Override
    public Stage createStage() {
        return new Stage();
    }

}
