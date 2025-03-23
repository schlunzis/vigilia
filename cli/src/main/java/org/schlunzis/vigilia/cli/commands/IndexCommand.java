package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.ui.Animation;
import org.schlunzis.vigilia.cli.ui.SpinnerAnimation;
import picocli.CommandLine;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@CustomLog
@CommandLine.Command(
        name = "index",
        aliases = "i",
        mixinStandardHelpOptions = true,
        description = "Indexes the given files for later querying"
)
public class IndexCommand implements Callable<Integer> {

    @CommandLine.Parameters
    private File[] files;

    @Override
    public Integer call() {
        Animation spinner = new SpinnerAnimation("Indexing files ");
        spinner.start();
        DefaultApi api = new DefaultApi();
        String[] paths = Stream.of(files).map(File::getAbsolutePath).toArray(String[]::new);

        try {
            api.indexFiles(List.of(paths));
        } catch (ApiException e) {
            spinner.stop();
            log.log(e.getMessage());
            log.log("Failed to index files. Make sure the paths are correct, the files are accessible and the service is running.");
            // TODO link documentation
            return 1;
        }
        spinner.stop();
        return 0;
    }

}
