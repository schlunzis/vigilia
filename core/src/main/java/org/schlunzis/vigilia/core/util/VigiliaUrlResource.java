package org.schlunzis.vigilia.core.util;

import lombok.NonNull;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLConnection;

/**
 * Custom UrlResource that sets a specific User-Agent header for HTTP requests.
 */
public class VigiliaUrlResource extends UrlResource {

    public VigiliaUrlResource(URI uri) throws MalformedURLException {
        super(uri);
    }

    public VigiliaUrlResource(String path) throws MalformedURLException {
        super(path);
    }

    @Override
    protected void customizeConnection(@NonNull URLConnection con) throws IOException {
        super.customizeConnection(con);
        con.addRequestProperty("User-Agent", "Vigilia Document Reader");
    }

}
