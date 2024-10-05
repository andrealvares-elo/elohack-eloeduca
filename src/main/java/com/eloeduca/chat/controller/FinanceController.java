package com.eloeduca.chat.controller;


import com.eloeduca.chat.model.Finance;
import com.eloeduca.chat.model.FinanceSummary;
import com.eloeduca.chat.repository.FinanceRepository;
import com.eloeduca.chat.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finances")
public class FinanceController {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private FinanceService financeService;

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

    @GetMapping("/summary")
    public FinanceSummary getSummary() {
        return financeService.getFinanceSummary();
    }
}