package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;
import org.schlunzis.vigilia.cli.ui.Animation;
import org.schlunzis.vigilia.cli.ui.ResultsPresenter;
import org.schlunzis.vigilia.cli.ui.SpinnerAnimation;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CustomLog
@CommandLine.Command(
        name = "query",
        aliases = "q",
        mixinStandardHelpOptions = true,
        description = "Queries indexed files and displays results"
)
public class QueryCommand implements Callable<Integer> {

    @CommandLine.Parameters
    private String[] args;

    @Override
    public Integer call() {
        Animation spinner = new SpinnerAnimation("Querying files ");
        spinner.start();
        DefaultApi api = new DefaultApi();
        String query = String.join(" ", args);

        try {
            List<SearchResultDTO> resultDTOS = api.searchFiles(query, null, null);
            spinner.stop();
            ResultsPresenter presenter = new ResultsPresenter();
            presenter.presentResults(resultDTOS);
        } catch (ApiException e) {
            spinner.stop();
            log.log(e.getMessage());
            log.log("Failed to query files. Make sure the service is running.");
            // TODO link documentation
            return 1;
        }
        spinner.stop();
        return 0;
    }

}
