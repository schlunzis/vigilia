package org.schlunzis.vigilia.gui.fx.tab;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.lib.TabType;

@Getter
public enum Type implements TabType {

    SEARCH("Search", false),
    SETTINGS("Settings", true);

    private final String tabName;
    private final boolean onlyOneInstance;

    Type(String tabName, boolean onlyOneInstance) {
        this.tabName = tabName;
        this.onlyOneInstance = onlyOneInstance;
    }

}
