package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CustomLog
public class ResultsPresenter {

    private CommandLine.Help.Ansi ansi = CommandLine.Help.Ansi.AUTO;

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
        String resultDisplay = ansi.string("@|bold,underline " + result.getScore() + ": " + result.getPath() + "|@ ");
        log.log(resultDisplay);
        if (result.getText() == null || result.getText().isBlank())
            return;
        String shortText = Arrays.stream(result.getText().split("\n"))
                .limit(3)
                .collect(Collectors.joining("\n"))
                .trim();
        shortText = ansi.string("@|italic " + shortText + "|@");
        log.log(shortText);
    }

    private void printSeparator() {
        log.log("----------");
    }

}
