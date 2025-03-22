package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.ui.Animation;
import org.schlunzis.vigilia.cli.ui.SpinnerAnimation;
import org.schlunzis.vigilia.cli.util.PathUtil;

import java.util.List;
import java.util.stream.Stream;

@CustomLog
public class IndexCommand extends AbstractCommand {

    protected IndexCommand(String[] args) {
        super(args);
    }

    @Override
    public void execute() {
        super.execute();

        Animation spinner = new SpinnerAnimation("Indexing files ");
        spinner.start();
        DefaultApi api = new DefaultApi();
        String[] paths = Stream.of(args).map(PathUtil::convertToAbsolutePath).toArray(String[]::new);

        try {
            api.indexFiles(List.of(paths));
        } catch (ApiException e) {
            spinner.stop();
            log.log("Failed to index files. Make sure the paths are correct, the files are accessible and the service is running.");
            log.log("StatusCode: " + e.getCode());
            // TODO link documentation
            System.exit(1);
        }
        spinner.stop();
    }

    @Override
    public void printHelp() {
        log.log("Usage: vig index <paths>");
        log.log("Index a directory or file");
        log.log("Paths can be relative or absolute");
    }

}
