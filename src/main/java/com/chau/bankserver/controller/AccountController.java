package com.chau.bankserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chau.bankserver.entity.Account;
import com.chau.bankserver.service.AccountService;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Create new Account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> body) {
         String name = body.get("name");
        if (name == null) {
           return ResponseEntity.badRequest().build();
        }

        Account account = accountService.addAccount(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // Get all accounts
    @GetMapping
    public Collection<Account> getAllAccounts() {
        return accountService.listAll();
    }

    // Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String id,@RequestBody Map<String, Double> body) {
        Double amount = body.get("amount");
        if (amount <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Account account = accountService.deposit(Long.parseLong(id), amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Withdraw money
    @PostMapping("/{id}/withdraw")
   public ResponseEntity<Account> withdraw(@PathVariable String id, @RequestBody Map<String, Double> body) {
        Double amount = body.get("amount");
        if (amount <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Account account = accountService.withdraw(Long.parseLong(id), amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Transfer money
    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody Map<String, Object> body) {
        long fromId = Long.parseLong(body.get("fromId").toString());
        long toId = Long.parseLong(body.get("toId").toString());
        Double amount = Double.parseDouble(body.get("amount").toString());

        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            accountService.transfer(fromId, toId, amount);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successful transfer");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
