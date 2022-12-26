package com.example.leftakias.controller;

import com.example.leftakias.entity.Cryptocurrency;
import com.example.leftakias.service.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cryptocurrencies")
public class CryptocurrencyController {
    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    // Returns the list of market cryptocurrencies
    @GetMapping("/api/cryptocurrencies/market")
    public List<Cryptocurrency> getMarketCryptocurrencies() {
        return this.cryptocurrencyService.getMarketCryptocurrencies();
    }

    // Returns the list of portfolio cryptocurrencies
    @GetMapping("/api/cryptocurrencies/portfolio")
    public List<Cryptocurrency> getPortfolioCryptocurrencies() {
        return this.cryptocurrencyService.getPortfolioCryptocurrencies();
    }

    // Adds a cryptocurrency to the portfolio
    @PostMapping("/add")
    public void addCryptocurrencyToPortfolio(@RequestParam("symbol") final String symbol,
                                             @RequestParam("amount") final double amount) {
        this.cryptocurrencyService.addCryptocurrencyToPortfolio(symbol, amount);
    }

    // Removes a cryptocurrency from the portfolio
    @PostMapping("/remove")
    public void removeCryptocurrencyFromPortfolio(@RequestParam("symbol") final String symbol) {
        this.cryptocurrencyService.removeCryptocurrencyFromPortfolio(symbol);
    }
}


