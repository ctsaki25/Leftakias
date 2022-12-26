import React, { useState, useEffect } from 'react';
import { Table, Form, Input, Button } from 'antd';
import './App.css';
import {toast} from "react-toastify";
import axios from "axios";

const CryptocurrenciesList = () => {
    const [marketCryptocurrencies, setMarketCryptocurrencies] = useState([]);
    const [portfolioCryptocurrencies, setPortfolioCryptocurrencies] = useState([]);

    useEffect(() => {
        // Retrieve market cryptocurrencies from backend
        axios.get('http://localhost:8080/api/cryptocurrencies/market')
            .then((response) => {
                setMarketCryptocurrencies(response.data);
            });

        // Retrieve portfolio cryptocurrencies from backend
        axios.get('http://localhost:8080/api/cryptocurrencies/portfolio')
            .then((response) => {
                setPortfolioCryptocurrencies(response.data);
            });
    }, []);

    const marketColumns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Symbol',
            dataIndex: 'symbol',
            key: 'symbol',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            render: (record) => (
                <Form
                    onFinish={(values) => {
                        // Send POST request to backend to add cryptocurrency to portfolio
                        axios.post('http://localhost:8080/api/cryptocurrencies/portfolio', {
                            symbol: record.symbol,
                            amount: values.amount,
                        })
                            .then((response) => {
                                // Update portfolio table with new data
                                setPortfolioCryptocurrencies(response.data);
                                toast.success('Added to portfolio', {
                                    position: toast.POSITION.TOP_RIGHT,
                                });
                            });
                    }}
                >
                    <Form.Item name="amount">
                        <Input placeholder="Amount" />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Buy
                        </Button>
                    </Form.Item>
                </Form>
            ),
        },
    ];

    const portfolioColumns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            align: 'center',
        },
        {
            title: 'Symbol',
            dataIndex: 'symbol',
            key: 'symbol',
            align: 'center',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
            align: 'center',
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            key: 'amount',
            align: 'center',
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            render: (record) => (
                <Form
                    onFinish={() => {
// Send DELETE request to backend to remove cryptocurrency from portfolio
                        fetch('http://localhost:8080/api/cryptocurrencies/portfolio/${record.symbol}', {
                            method: 'DELETE',
                        })
                            .then((response) => response.json())
                            .then((data) => {
// Update portfolio table with new data
                                setPortfolioCryptocurrencies(data);
                                toast.success('Removed from portfolio', {
                                    position: toast.POSITION.TOP_RIGHT,
                                });
                            });
                    }}
                >
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Sell
                        </Button>
                    </Form.Item>
                </Form>
            ),
        },
    ];
    const tableContainerStyles = {
        display: 'flex',
        justifyContent: 'space-around',
    };

    const tableStyles = {
        width: '45%',
    };


    return (
        <div>
            <h1 style={{ textAlign: 'center' }}>Leftakias Crypto Portfolio</h1>
            <div style={tableContainerStyles}>
                <Table
                    style={tableStyles}
                    columns={marketColumns}
                    dataSource={marketCryptocurrencies}
                    title={() => <h3 style={{ textAlign: 'center' }}>Market Cryptocurrencies</h3>}
                    pagination={false}
                />
                <Table
                    style={tableStyles}
                    columns={portfolioColumns}
                    dataSource={portfolioCryptocurrencies}
                    title={() => <h3 style={{ textAlign: 'center' }}>Portfolio Cryptocurrencies</h3>}
                    pagination={false}
                />
            </div>
        </div>
    );
};

export default CryptocurrenciesList;