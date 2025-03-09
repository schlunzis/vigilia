package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.control.Tab;

@FunctionalInterface
public interface TabFactory<T extends TabType> {

    Tab createTab(T tabType);

}
