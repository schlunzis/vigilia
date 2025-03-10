package org.schlunzis.vigilia.gui.fx.lib;

import javafx.stage.Stage;

public interface TabbedStagesManager<T extends TabType> {

    static <T extends TabType> TabbedStagesManager<T> create(Stage primaryStage) {
        return new TabbedStagesManagerImpl<>(primaryStage);
    }

    void setStageFactory(StageFactory stageFactory);

    void setTabFactory(TabFactory<T> tabFactory);

    void setTabContentFactory(TabContentFactory<T> tabContentFactory);

    void addTab(T tab);

    void addTab(int stageId, T tab);

    void setDefaultTabType(T type);

}
