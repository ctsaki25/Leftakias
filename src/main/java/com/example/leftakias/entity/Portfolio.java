package com.example.leftakias.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@Table(name = "portfolios")
public class Portfolio {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private List<PortfolioCryptocurrency> cryptocurrencies;

    public Portfolio() {}

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<PortfolioCryptocurrency> getCryptocurrencies() {
        return this.cryptocurrencies;
    }

    public void setCryptocurrencies(final List<PortfolioCryptocurrency> cryptocurrencies) {
        this.cryptocurrencies = cryptocurrencies;
    }
}
