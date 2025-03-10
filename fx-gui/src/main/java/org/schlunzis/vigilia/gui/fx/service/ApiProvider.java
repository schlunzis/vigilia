package org.schlunzis.vigilia.gui.fx.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.schlunzis.vigilia.gui.fx.api.DefaultApi;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
final class ApiProvider {

    @Getter
    private static final DefaultApi defaultApi;

    static {
        defaultApi = new DefaultApi();
    }

}
