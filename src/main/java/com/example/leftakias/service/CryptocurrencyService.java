package com.example.leftakias.service;

import com.example.leftakias.controller.CryptocurrencyData;
import com.example.leftakias.entity.Cryptocurrency;
import com.example.leftakias.entity.Portfolio;
import com.example.leftakias.entity.PortfolioCryptocurrency;
import com.example.leftakias.repository.CryptocurrencyRepository;
import com.example.leftakias.repository.PortfolioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CryptocurrencyService {
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private RestTemplate restTemplate;
    //@Value("${coinmarketcap.api.key}")
    private String apiKey = "8275436f-b4b3-42a2-aaeb-1c0e642fb7bf";

    public List<Cryptocurrency> getMarketCryptocurrencies() {
        // Retrieve the top 15 cryptocurrencies from CoinMarketCap API
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY",
                this.apiKey);
        final HttpEntity<String> entity = new HttpEntity<>("body", headers);

        final ResponseEntity<CryptocurrencyData[]> response = this.restTemplate.exchange(
                "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=15&sort=market_cap&sort_dir=desc",
                HttpMethod.GET,
                entity,
                CryptocurrencyData[].class);

        final CryptocurrencyData[] data = response.getBody();
        assert null != data;
        final Cryptocurrency[] cryptocurrencies = new Cryptocurrency[data.length];
        for (int i = 0; i < data.length; i++) {
            final CryptocurrencyData cryptocurrencyData = data[i];
            final Cryptocurrency cryptocurrency = new Cryptocurrency(
                    cryptocurrencyData.getName(),
                    cryptocurrencyData.getSymbol(),
                    cryptocurrencyData.getQuote().getUsd().getPrice(),
                    0);
            cryptocurrencies[i] = cryptocurrency;
        }

// Return the list of Cryptocurrency objects
        return Arrays.asList(cryptocurrencies);
    }

    @Transactional
    public List<Cryptocurrency> getPortfolioCryptocurrencies() {
        // Retrieve the portfolio from the database
        final Portfolio portfolio = this.portfolioRepository.findById(1L).orElse(null);

        // If the portfolio doesn't exist, return an empty list
        if (null == portfolio) {
            return Collections.emptyList();
        }

        // Convert the portfolio's list of PortfolioCryptocurrency objects to a list of Cryptocurrency objects
        final List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
        for (final PortfolioCryptocurrency portfolioCryptocurrency : portfolio.getCryptocurrencies()) {
            final Cryptocurrency cryptocurrency = new Cryptocurrency();
            cryptocurrency.setName(portfolioCryptocurrency.getName());
            cryptocurrency.setSymbol(portfolioCryptocurrency.getSymbol());
            cryptocurrency.setPrice(portfolioCryptocurrency.getPrice());
            cryptocurrency.setAmount(portfolioCryptocurrency.getAmount());
            cryptocurrencies.add(cryptocurrency);
        }

        // Return the list of Cryptocurrency objects
        return cryptocurrencies;
    }

    public void addCryptocurrencyToPortfolio(final String symbol, final double amount) {
        Cryptocurrency cryptocurrency = this.cryptocurrencyRepository.findBySymbol(symbol);
        if (null == cryptocurrency) {
            // Retrieve the cryptocurrency data from CoinMarketCap API
            final RestTemplate restTemplate = new RestTemplate();
            final HttpHeaders headers = new HttpHeaders();
            headers.set("X-CMC_PRO_API_KEY", this.apiKey);
            final HttpEntity<String> entity = new HttpEntity<>("body", headers);
            final ResponseEntity<CryptocurrencyData[]> response = restTemplate.exchange(
                    "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?symbol=" + symbol,
                    HttpMethod.GET,
                    entity,
                    CryptocurrencyData[].class);
            final CryptocurrencyData cryptocurrencyData = response.getBody()[0];
            cryptocurrency = new Cryptocurrency(
                    cryptocurrencyData.getName(),
                    cryptocurrencyData.getSymbol(),
                    cryptocurrencyData.getQuote().getUsd().getPrice(),
                    0);
            // Save the cryptocurrency to the database
            this.cryptocurrencyRepository.save(cryptocurrency);
        }

        // Retrieve the portfolio from the database
        Portfolio portfolio = this.portfolioRepository.findById(1L).orElse(null);
        if (null == portfolio) {
            // Create a new portfolio
            portfolio = new Portfolio();
            portfolio.setId(1L);
            portfolio.setCryptocurrencies(new ArrayList<>());
        }

        // Check if the cryptocurrency is already in the portfolio
        PortfolioCryptocurrency portfolioCryptocurrency = null;
        for (final PortfolioCryptocurrency p : portfolio.getCryptocurrencies()) {
            if (p.getSymbol().equals(symbol)) {
                portfolioCryptocurrency = p;
                break;
            }
        }
        if (null == portfolioCryptocurrency) {
            // Add the cryptocurrency to the portfolio
            portfolioCryptocurrency = new Cryptocurrency();
            portfolioCryptocurrency.setName(cryptocurrency.getName());
            portfolioCryptocurrency.setSymbol(cryptocurrency.getSymbol());
            portfolioCryptocurrency.setPrice(cryptocurrency.getPrice());
            portfolioCryptocurrency.setAmount(amount);
            portfolio.getCryptocurrencies().add(portfolioCryptocurrency);
        } else {
            // Update the amount of the cryptocurrency in the portfolio
            portfolioCryptocurrency.setAmount(portfolioCryptocurrency.getAmount() + amount);
        }

        // Save the portfolio to the database
        this.portfolioRepository.save(portfolio);
    }


    public void removeCryptocurrencyFromPortfolio(final String symbol) {
        // Retrieve the portfolio from the database
        final Portfolio portfolio = this.portfolioRepository.findById(1L).orElse(null);

        // If the portfolio doesn't exist, do nothing
        if (null == portfolio) {
            return;
        }

        // Find the PortfolioCryptocurrency object with the given symbol
        PortfolioCryptocurrency portfolioCryptocurrency = null;
        for (final PortfolioCryptocurrency p : portfolio.getCryptocurrencies()) {
            if (p.getSymbol().equals(symbol)) {
                portfolioCryptocurrency = p;
                break;
            }
        }

        // If the PortfolioCryptocurrency object was not found, do nothing
        if (null == portfolioCryptocurrency) {
            return;
        }

        // Remove the PortfolioCryptocurrency object from the portfolio's list of cryptocurrencies
        portfolio.getCryptocurrencies().remove(portfolioCryptocurrency);

        // Save the updated portfolio to the database
        this.portfolioRepository.save(portfolio);
    }
}
