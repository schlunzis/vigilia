package org.schlunzis.vigilia.gui.fx.service;

import lombok.Getter;

public class IndexService {

    @Getter
    private static final IndexService INSTANCE = new IndexService();

    private IndexService() {
        
    }

}
