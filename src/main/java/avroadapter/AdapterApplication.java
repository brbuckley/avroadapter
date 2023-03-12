package avroadapter;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

/** Springboot main class. */
@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan("avroadapter.configuration")
public class AdapterApplication {

  @PostConstruct
  void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  /**
   * Springboot main method.
   *
   * @param args CLI arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(AdapterApplication.class, args);
  }
}
