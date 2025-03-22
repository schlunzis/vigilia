package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;
import org.schlunzis.vigilia.cli.ui.Animation;
import org.schlunzis.vigilia.cli.ui.ResultsPresenter;
import org.schlunzis.vigilia.cli.ui.SpinnerAnimation;

import java.util.List;

@CustomLog
public class QueryCommand extends AbstractCommand {

    protected QueryCommand(String[] args) {
        super(args);
    }

    @Override
    public void execute() {
        super.execute();
        if (args.length == 0) {
            log.log("No query provided");
            printHelp();
            System.exit(1);
        }

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
            log.log("Failed to query files. Make sure the service is running.");
            log.log("StatusCode: " + e.getCode());
            // TODO link documentation
            System.exit(1);
        }
    }

    @Override
    public void printHelp() {

    }

}
