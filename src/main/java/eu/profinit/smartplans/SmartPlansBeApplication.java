package eu.profinit.smartplans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SmartPlansBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPlansBeApplication.class, args);
    }

}
