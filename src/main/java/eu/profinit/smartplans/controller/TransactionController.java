package eu.profinit.smartplans.controller;

import eu.profinit.smartplans.api.TransactionApi;
import eu.profinit.smartplans.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/transaction/{id}")
    public TransactionApi getTransaction(@PathVariable("id") Long id) {

        return transactionService.getTransaction(id);
    }
}
