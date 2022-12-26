package com.example.leftakias.controller;

import com.example.leftakias.entity.Cryptocurrency;
import com.example.leftakias.service.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    public PortfolioController(final CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping
    public List<Cryptocurrency> getPortfolio() {
        return this.cryptocurrencyService.getPortfolioCryptocurrencies();
    }

    @PostMapping("/add")
    public void addCryptocurrencyToPortfolio(@RequestParam("symbol") final String symbol, @RequestParam("amount") final double amount) {
        this.cryptocurrencyService.addCryptocurrencyToPortfolio(symbol, amount);
    }

    @DeleteMapping("/remove")
    public void removeCryptocurrencyFromPortfolio(@RequestParam("symbol") final String symbol) {
        this.cryptocurrencyService.removeCryptocurrencyFromPortfolio(symbol);
    }

}
