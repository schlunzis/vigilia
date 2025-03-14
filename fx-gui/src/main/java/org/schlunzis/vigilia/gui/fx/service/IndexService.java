package org.schlunzis.vigilia.gui.fx.service;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.ApiException;
import org.schlunzis.vigilia.gui.fx.ApiResponse;
import org.schlunzis.vigilia.gui.fx.api.DefaultApi;

import java.util.List;

public class IndexService {

    @Getter
    private static final IndexService instance = new IndexService();
    private final DefaultApi defaultApi;

    private IndexService() {
        this.defaultApi = ApiProvider.getDefaultApi();
    }

    public boolean index(List<String> paths) {
        try {
            ApiResponse<Void> response = defaultApi.indexFilesWithHttpInfo(paths);
            return response.getStatusCode() == 200;
        } catch (ApiException e) {
            return false;
        }
    }

}
