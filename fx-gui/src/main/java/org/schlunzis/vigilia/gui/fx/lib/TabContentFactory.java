package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.Node;

@FunctionalInterface
public interface TabContentFactory<T extends TabType> {

    Node create(T tabType);

}
