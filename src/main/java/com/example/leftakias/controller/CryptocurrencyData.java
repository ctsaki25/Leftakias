package com.example.leftakias.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@Component
public class CryptocurrencyData {
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("quote")
    private Quote quote;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public Quote getQuote() {
        return this.quote;
    }

    public void setQuote(final Quote quote) {
        this.quote = quote;
    }

    public double getPrice() {
        return this.quote.getPrice();
    }

    public static class Quote {
        @JsonProperty("USD")
        private Usd usd;

        public Usd getUsd() {
            return this.usd;
        }

        public void setUsd(final Usd usd) {
            this.usd = usd;
        }

        public double getPrice() {
            return this.usd.getPrice();
        }

        public static class Usd {
            @JsonProperty("price")
            private double price;

            public double getPrice() {
                return this.price;
            }

            public void setPrice(final double price) {
                this.price = price;
            }
        }
    }

}
