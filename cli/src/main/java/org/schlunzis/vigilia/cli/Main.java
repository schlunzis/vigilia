package org.schlunzis.vigilia.cli;

import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ApiException {
        DefaultApi api = new DefaultApi();
        List<SearchResultDTO> resultDTOS = api.searchFiles("test");
        for (SearchResultDTO resultDTO : resultDTOS) {
            System.out.println(resultDTO);
        }
    }

}
