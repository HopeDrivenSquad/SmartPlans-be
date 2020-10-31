package eu.profinit.smartplans.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping
    public String hello() {
        return "Ahoj teame";
    }
}
