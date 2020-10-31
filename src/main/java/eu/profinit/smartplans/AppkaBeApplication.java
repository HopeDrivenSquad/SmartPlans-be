package eu.profinit.smartplans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AppkaBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppkaBeApplication.class, args);
    }

}
