package org.schlunzis.vigilia.cli;

import lombok.CustomLog;
import org.apache.commons.cli.*;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.DatabaseConfigDTO;
import org.schlunzis.vigilia.cli.model.SearchResultDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@CustomLog
public class CLIApplication {

    private static final Options OPTIONS = new Options();

    static {
        OPTIONS.addOption("h", "help", false, "Print this message");
        OPTIONS.addOption("v", "version", false, "Print version information");

        OPTIONS.addOption(Option.builder()
                .option("i")
                .longOpt("index")
                .hasArgs()
                .argName("paths")
                .desc("Index a file or directory")
                .build());
        OPTIONS.addOption(Option.builder()
                .option("q")
                .longOpt("query")
                .hasArgs()
                .argName("query")
                .desc("Query indexed files")
                .build());
        OPTIONS.addOption(Option.builder()
                .option("d")
                .longOpt("database")
                .hasArgs()
                .argName("config")
                .desc("Database configuration <url> <username> <password>")
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


        if (cmd.hasOption("d")) {
            String[] configParts = cmd.getOptionValues("d");
            if (configParts.length != 3) {
                log.log("Database configuration requires 3 arguments: <url> <username> <password>");
                System.exit(1);
            }
            String url = configParts[0];
            String username = configParts[1];
            String password = configParts[2];
            DatabaseConfigDTO databaseConfigDTO = new DatabaseConfigDTO().url(url).username(username).password(password);
            api.updateDatabaseConfig(databaseConfigDTO);
        }

        if (cmd.hasOption("i")) {
            String[] paths = cmd.getOptionValues("i");
            paths = Stream.of(paths).map(CLIApplication::convertToAbsolutePath).toArray(String[]::new);
            api.indexFiles(List.of(paths));
        }

        if (cmd.hasOption("q")) {
            String[] queryParts = cmd.getOptionValues("q");
            String query = String.join(" ", queryParts);
            List<SearchResultDTO> resultDTOS = api.searchFiles(query, null, null);
            for (SearchResultDTO resultDTO : resultDTOS) {
                log.log(resultDTO);
            }
        }
    }

    private static String convertToAbsolutePath(String path) {
        return Path.of(path).toAbsolutePath().toString();
    }

}
