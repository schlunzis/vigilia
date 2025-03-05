package org.schlunzis.vigilia.gui.fx.tab;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.lib.TabType;

public enum Type implements TabType {

    SEARCH("Search"),
    SETTINGS("Settings");

    @Getter
    private final String tabName;

    Type(String tabName) {
        this.tabName = tabName;
    }

}
