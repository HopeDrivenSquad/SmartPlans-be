package eu.profinit.smartplans.controller;

import eu.profinit.smartplans.api.TransactionApi;
import eu.profinit.smartplans.api.TransactionOverview;
import eu.profinit.smartplans.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    @GetMapping
    public TransactionOverview getOverview(@RequestParam Long clientId) {

        return transactionService.getOverview(clientId);
    }

    @GetMapping(value = "/{id}")
    public TransactionApi getTransaction(@PathVariable("id") Long id) {

        return transactionService.getTransaction(id);
    }
}
