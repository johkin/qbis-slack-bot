package se.acrend.slack.qbis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import se.acrend.slack.qbis.config.ApplicationConfig;

/**
 *
 */
@SpringBootApplication(scanBasePackages = {"se.acrend.slack.qbis",
        "me.ramswaroop.jbot.core"})
@Import(ApplicationConfig.class)
public class QbisBotApplication {
    /**
     * Entry point of the application. Run this method to start the sample bots,
     * but don't forget to add the correct tokens in application.properties file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(QbisBotApplication.class, args);
    }

}
