package br.com.itau.pixexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.itau.pixexample.repository")
public class PixExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixExampleApplication.class, args);
    }

}
