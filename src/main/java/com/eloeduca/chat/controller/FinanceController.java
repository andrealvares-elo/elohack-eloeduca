package com.eloeduca.chat.controller;


import com.eloeduca.chat.model.Finance;
import com.eloeduca.chat.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finances")
public class FinanceController {

    @Autowired
    private FinanceRepository financeRepository;

    @GetMapping
    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    @PostMapping
    public Finance createFinance(@RequestBody Finance finance) {
        return financeRepository.save(finance);
    }

    @PostMapping("/many")
    public List<Finance> createManyFinances(@RequestBody List<Finance> finances) {
        return financeRepository.saveAll(finances);
    }

    @DeleteMapping("/{id}")
    public void deleteFinance(@PathVariable Long id) {
        financeRepository.deleteById(id);
    }
}