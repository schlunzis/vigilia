package org.schlunzis.vigilia.gui.fx.lib;

import javafx.stage.Stage;

@FunctionalInterface
public interface StageFactory {

    Stage createStage();

}
