package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CustomLog
public class ResultsPresenter {

    public void presentResults(List<SearchResultDTO> results) {
        for (int i = 0; i < results.size() - 1; i++) {
            presentResult(results.get(i));
            printSeparator();
        }
        if (!results.isEmpty())
            presentResult(results.getLast());
        else
            log.log("No results found.");
    }

    private void presentResult(SearchResultDTO result) {
        log.log("\u001B[1m{0}: {1}\u001B[0m", result.getScore(), result.getPath());
        if (result.getText() == null || result.getText().isBlank())
            return;
        String shortText = Arrays.stream(result.getText().split("\n"))
                .limit(3)
                .collect(Collectors.joining("\n"));
        log.log(shortText);
    }

    private void printSeparator() {
        log.log("----------");
    }

}
