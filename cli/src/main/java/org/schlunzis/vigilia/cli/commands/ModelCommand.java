package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.ApiException;
import org.schlunzis.vigilia.cli.api.DefaultApi;
import org.schlunzis.vigilia.cli.model.AddModelRequestDTO;
import org.schlunzis.vigilia.cli.model.ModelDTO;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CustomLog
@CommandLine.Command(name = "model",
        aliases = "m",
        mixinStandardHelpOptions = true,
        description = "Add a new model to the server")
public class ModelCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Name of the model")
    private String name;
    @CommandLine.Parameters(index = "1", description = "Path to the model")
    private String modelPath;
    @CommandLine.Parameters(index = "2", description = "Path to the tokenizer")
    private String tokenizerPath;
    @CommandLine.Parameters(index = "3", description = "Pooling mode", arity = "0..1", defaultValue = "CLS")
    private String poolingMode;

    @Override
    public Integer call() {
        try {
            DefaultApi api = new DefaultApi();
            ModelDTO modelDTO = new ModelDTO()
                    .name(name)
                    .modelPath(modelPath)
                    .tokenizerPath(tokenizerPath)
                    .poolingMode(ModelDTO.PoolingModeEnum.valueOf(poolingMode));
            api.addModel(new AddModelRequestDTO().model(modelDTO));
            log.log("Model added to the server.");
            return 0;
        } catch (ApiException e) {
            log.log("Failed to add a model to the server.");
            log.log("StatusCode: {0}", e.getCode());
            return 1;
        }
    }
}
