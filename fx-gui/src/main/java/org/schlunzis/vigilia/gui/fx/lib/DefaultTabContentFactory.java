package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.Objects;

public class DefaultTabContentFactory<T extends TabType> implements TabContentFactory<T> {

    @Override
    public Node create(T tabType) {
        return new Label(Objects.toString(tabType));
    }

}
