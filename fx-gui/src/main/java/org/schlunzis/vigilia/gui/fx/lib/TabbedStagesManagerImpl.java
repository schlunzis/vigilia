package org.schlunzis.vigilia.gui.fx.lib;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class TabbedStagesManagerImpl<T extends TabType> implements TabbedStagesManager<T> {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, Stage> stages = new HashMap<>();
    private final Map<Integer, MainController<T>> controllers = new HashMap<>();
    private final Map<T, Tab> tabs = new HashMap<>();

    @Setter
    private StageFactory stageFactory = new DefaultStageFactory();
    @Getter
    private TabFactory<T> tabFactory;
    @Setter
    @Getter
    private TabContentFactory<T> tabContentFactory = new DefaultTabContentFactory<>();
    @Setter
    @Getter
    private T defaultTabType;

    TabbedStagesManagerImpl(Stage primaryStage) {
        final int stageId = counter.getAndIncrement();
        setTabFactory(new DefaultTabFactory<>());
        registerStage(primaryStage, stageId);
        setupStage(primaryStage, stageId);
    }

    public void setTabFactory(TabFactory<T> tabFactory) {
        this.tabFactory = tabFactory;
        if (tabFactory instanceof DefaultTabFactory<T> defaultTabFactory) {
            defaultTabFactory.setTabbedStagesManager(this);
        }
    }

    public void tabDraggedOutsideTabPane(Tab tab, Point2D screenCoordinates) {
        tab.getTabPane().getTabs().remove(tab);
        Stage newStage = createNewStage();
        newStage.setX(screenCoordinates.getX());
        newStage.setY(screenCoordinates.getY());
        MainController<T> controller = controllers.get(counter.get() - 1);
        controller.addTab(tab);
        newStage.show();
    }

    @Override
    public void addTab(T tab) {
        addTab(0, tab);
    }

    @Override
    public void addTab(int stageId, T tabType) {
        MainController<T> controllerToAddTheTabTo = controllers.get(stageId);

        Tab newTab;
        if (tabType.isOnlyOneInstance()) {
            newTab = tabs.computeIfAbsent(tabType, t -> tabFactory.createTab(t));
            TabPane currentTabPane = newTab.getTabPane();
            if (currentTabPane != null) currentTabPane.getTabs().remove(newTab);
        } else {
            newTab = tabFactory.createTab(tabType);
            Node newTabContent = tabContentFactory.create(tabType);
            newTab.setContent(newTabContent);
        }

        controllerToAddTheTabTo.addTab(newTab);
    }

    Stage createNewStage() {
        Stage stage = stageFactory.createStage();
        final int stageId = counter.getAndIncrement();
        registerStage(stage, stageId);
        setupStage(stage, stageId);
        return stage;
    }

    private void registerStage(Stage stage, int stageId) {
        stage.setOnCloseRequest(_ -> {
            stages.remove(stageId);
            controllers.remove(stageId);
        });
        stages.put(stageId, stage);
    }

    private void setupStage(Stage stage, int stageId) {
        MainView mainView = new MainView();
        controllers.put(stageId, new MainController<>(this, mainView));
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
    }

}
