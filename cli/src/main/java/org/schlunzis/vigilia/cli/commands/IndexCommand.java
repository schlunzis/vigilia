package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.IndexFilesRequestDTO;
import org.schlunzis.vigilia.cli.ui.Animation;
import org.schlunzis.vigilia.cli.ui.SpinnerAnimation;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
        List<String> paths = Stream.of(files)
                .map(f -> {
                    if (!f.exists()) {
                        log.log("File " + f.getAbsolutePath() + " does not exist. Skipping.");
                        return null;
                    }
                    try {
                        return f.getCanonicalPath();
                    } catch (IOException e) {
                        log.log("Failed to get canonical path for " + f.getAbsolutePath() + ". Skipping.");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        if (paths.isEmpty()) {
            log.log("No files to index.");
            return 0;
        }

        Animation spinner = new SpinnerAnimation("Indexing files ");
        spinner.start();
        DefaultApi api = new DefaultApi();
        try {
            api.indexFiles(new IndexFilesRequestDTO().paths(paths));
        } catch (ApiException e) {
            spinner.stop();
            log.log("Failed to index files. Make sure the paths are correct, the files are accessible and the service is running.");
            log.log("StatusCode: " + e.getCode());
            // TODO link documentation
            return 1;
        }
        spinner.stop();
        log.log("Successfully indexed files.");
        return 0;
    }

}
