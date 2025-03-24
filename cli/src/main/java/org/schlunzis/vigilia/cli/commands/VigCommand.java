package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ui.InputPresenter;
import org.schlunzis.vigilia.cli.ui.OptionPresenter;
import picocli.CommandLine;

@CustomLog
@CommandLine.Command(
        name = "vig",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        description = "CLI for the vigilia ecosystem",
        subcommands = {
                IndexCommand.class,
                QueryCommand.class
        }
)
public class VigCommand implements Runnable {

    @Override
    public void run() {
        OptionPresenter presenter = new OptionPresenter("What do you want to do?",
                "Index new files",
                "Query indexed files");
        int selectedOption = presenter.present();
        log.log("");

        switch (selectedOption) {
            case 0:
                handleIndex();
                break;
            case 1:
                handleQuery();
                break;
            default:
                log.log("Unsupported option");
        }
    }

    private void handleIndex() {
        OptionPresenter presenter = new OptionPresenter("Which files do you want to index?",
                "This directory",
                "File or subdirectory");
        int selectedOption = presenter.present();
        log.log("");

        switch (selectedOption) {
            case 0:
                handleIndexDirectory();
                break;
            case 1:
                handleIndexFile();
                break;
            default:
                log.log("Unsupported option");
        }
    }

    private void handleIndexDirectory() {
        CommandLine commandLine = new CommandLine(new IndexCommand());
        int exitCode = commandLine.execute(".");
        System.exit(exitCode);
    }

    private void handleIndexFile() {
        InputPresenter presenter = new InputPresenter("Enter the name of the file or subdirectory to index");
        String fileName = presenter.present();
        log.log("");

        CommandLine commandLine = new CommandLine(new IndexCommand());
        int exitCode = commandLine.execute(fileName);
        System.exit(exitCode);
    }

    private void handleQuery() {
        InputPresenter presenter = new InputPresenter("Enter the query");
        String query = presenter.present();
        log.log("");

        CommandLine commandLine = new CommandLine(new QueryCommand());
        int exitCode = commandLine.execute(query);
        System.exit(exitCode);
    }

}
