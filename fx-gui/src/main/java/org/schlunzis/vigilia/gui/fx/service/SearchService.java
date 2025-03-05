package org.schlunzis.vigilia.gui.fx.service;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.ApiException;
import org.schlunzis.vigilia.gui.fx.ApiResponse;
import org.schlunzis.vigilia.gui.fx.api.DefaultApi;
import org.schlunzis.vigilia.gui.fx.model.SearchResultDTO;

import java.util.List;

public class SearchService {

    @Getter
    private static final SearchService instance = new SearchService();

    private final DefaultApi defaultApi = ApiProvider.getDefaultApi();

    private SearchService() {

    }

    public List<SearchResultDTO> search(String query) {
        List<SearchResultDTO> results = null;
        try {
            ApiResponse<List<SearchResultDTO>> response = defaultApi.searchFilesWithHttpInfo(query);
            if (response.getStatusCode() == 200) {
                results = response.getData();
            } else {
                System.err.println("Error: " + response.getStatusCode());
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#searchFiles");
            e.printStackTrace();
        }
        return results;
    }

}
