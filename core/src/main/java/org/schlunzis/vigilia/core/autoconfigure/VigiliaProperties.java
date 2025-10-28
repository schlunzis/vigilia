package org.schlunzis.vigilia.core.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "vigilia.data")
public class VigiliaProperties {

    private String appdir;

    private Datasource datasource = new Datasource();

    @Data
    private static class Datasource {
        private String url;
    }
}
