package org.schlunzis.vigilia.gui.fx;

import org.schlunzis.vigilia.gui.fx.api.DefaultApi;

public class Main {

    public static void main(String[] args) throws ApiException {
        DefaultApi api = new DefaultApi();
        api.searchFiles("Hello World").forEach(System.out::println);
    }

}
