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
                QueryCommand.class,
                ModelCommand.class
        }
)
public class VigCommand implements Runnable {

    @Override
    public void run() {
        OptionPresenter presenter = new OptionPresenter("What do you want to do?",
                "Index new files",
                "Query indexed files",
                "Add a new model");
        int selectedOption = presenter.present();
        log.log("");

        switch (selectedOption) {
            case 0:
                handleIndex();
                break;
            case 1:
                handleQuery();
                break;
            case 2:
                handleAddModel();
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

    private void handleAddModel() {
        // TODO: somehow this is broken: getting multiple arguments does not work...
        InputPresenter presenter = new InputPresenter("Enter a name for the model");
        String name = presenter.present();
        log.log("");
        presenter = new InputPresenter("Enter the path to the model");
        String modelPath = presenter.present();
        log.log("");
        presenter = new InputPresenter("Enter the path to the tokenizer");
        String tokenizerPath = presenter.present();
        log.log("");

        CommandLine commandLine = new CommandLine(new ModelCommand());
        int exitCode = commandLine.execute(name, modelPath, tokenizerPath);
        System.exit(exitCode);
    }

}
