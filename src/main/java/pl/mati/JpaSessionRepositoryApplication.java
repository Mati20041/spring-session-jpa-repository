package pl.mati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaSessionRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
