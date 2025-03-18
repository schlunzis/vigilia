package org.schlunzis.vigilia.core;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoreApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(CoreApplication.class, args);
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(CoreApplication.class, args.getSourceArgs());
            System.out.println("Restart new Thread done!");
        });

        thread.setDaemon(false);
        thread.start();
        System.out.println("restart Method finished!");
    }

}
