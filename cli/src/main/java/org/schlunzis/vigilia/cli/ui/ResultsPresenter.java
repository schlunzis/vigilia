package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;
import org.schlunzis.vigilia.cli.util.AnsiProvider;
import org.schlunzis.vigilia.cli.util.ColorSelector;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CustomLog
public class ResultsPresenter {

    private static final CommandLine.Help.Ansi ansi = AnsiProvider.getAnsi();

    private double minScore = 0.0;
    private double maxScore = 0.0;

    public void presentResults(List<SearchResultDTO> results) {
        //noinspection DataFlowIssue
        minScore = results.stream().mapToDouble(SearchResultDTO::getScore).min().orElse(0.0);
        //noinspection DataFlowIssue
        maxScore = results.stream().mapToDouble(SearchResultDTO::getScore).max().orElse(0.0);

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
        @SuppressWarnings("DataFlowIssue") double score = result.getScore();
        // TODO get absolute min and max for the query from the server
        String scoreColor = ColorSelector.getColor(minScore, maxScore, score);
        String formattedScore = String.format("%.3f", score);
        String resultDisplay = ansi.string("@|" + scoreColor + " " + formattedScore + "|@: @|bold,underline " + result.getPath() + "|@ ");
        log.log(resultDisplay);
        if (result.getText() == null || result.getText().isBlank())
            return;
        String shortText = Arrays.stream(result.getText().split("\n"))
                .limit(3)
                .collect(Collectors.joining("\n"))
                .trim();
        log.log(shortText);
    }

    private void printSeparator() {
        log.log("----------");
    }

}
