package org.schlunzis.vigilia.cli;

import lombok.CustomLog;
import org.apache.commons.cli.*;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;

import java.util.List;

@CustomLog
public class CLIApplication {

    private static final Options OPTIONS = new Options();

    static {
        OPTIONS.addOption("h", "help", false, "Print this message");
        OPTIONS.addOption("v", "version", false, "Print version information");
        OPTIONS.addOption(Option.builder()
                .option("i")
                .longOpt("index")
                .hasArg()
                .argName("path")
                .desc("Index a file or directory")
                .build());
        OPTIONS.addOption(Option.builder()
                .option("s")
                .longOpt("search")
                .hasArg()
                .argName("query")
                .desc("Search indexed files")
                .build());
    }

    public static void main(String[] args) throws ApiException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(OPTIONS, args);
        } catch (UnrecognizedOptionException e) {
            log.log("Unrecognized option: {0}", e.getOption());
            System.exit(1);
        } catch (Exception e) {
            log.log("Error parsing command line arguments", e);
            System.exit(1);
        }

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("vig [options]", OPTIONS);
            return;
        }
        if (cmd.hasOption("v")) {
            log.log(CLIApplication.class.getPackage().getImplementationVersion());
            return;
        }


        DefaultApi api = new DefaultApi();
        api.indexFiles(List.of("test"));


        List<SearchResultDTO> resultDTOS = api.searchFiles("test");
        for (SearchResultDTO resultDTO : resultDTOS) {
            log.log(resultDTO);
        }
    }

}
