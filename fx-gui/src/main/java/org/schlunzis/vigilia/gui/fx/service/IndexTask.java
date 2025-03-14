package org.schlunzis.vigilia.gui.fx.service;

import javafx.concurrent.Task;
import lombok.Getter;

import java.util.List;

@Getter
public class IndexTask extends Task<Void> {

    private final List<String> paths;

    public IndexTask(List<String> paths) {
        this.paths = paths;
        this.updateTitle(paths.getFirst());
    }

    @Override
    protected Void call() {
        updateProgress(0, paths.size());
        updateMessage("Indexing...");

        IndexService.getInstance().index(paths);

        updateProgress(paths.size(), paths.size());
        updateMessage("Done");
        return null;
    }

}
