package org.schlunzis.vigilia.gui.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.schlunzis.vigilia.gui.fx.api.DefaultApi;
import org.schlunzis.vigilia.gui.fx.lib.TabbedStagesManager;
import org.schlunzis.vigilia.gui.fx.tab.Type;
import org.schlunzis.vigilia.gui.fx.tab.VTabContentFactory;

public class VigiliaApplication extends Application {

    public static void main(String[] args) throws ApiException {
        DefaultApi api = new DefaultApi();
        api.searchFiles("Hello World").forEach(System.out::println);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TabbedStagesManager<Type> tabbedStagesManager = TabbedStagesManager.create(stage);
        tabbedStagesManager.setDefaultTabType(Type.SEARCH);
        tabbedStagesManager.setTabContentFactory(new VTabContentFactory(tabbedStagesManager));
        tabbedStagesManager.addTab(Type.SEARCH);
        stage.show();
    }

}
