# leftakias

A cryptocurrency portfolio tracker application built with React and Springboot. With leftakias, you can keep track of your cryptocurrency investments and see how they're performing in real-time. Uses the coinmarketcap api to fetch cryptocurrency data.

## Features

1. View a list of market cryptocurrencies and their current prices.
2. Add cryptocurrencies to your portfolio by specifying the symbol and amount.
3. See the total value of your portfolio and how it's changed over time.
4. Remove cryptocurrencies from your portfolio.
5. View the historical price data for each cryptocurrency in your portfolio.

## Requirements

- Node.js
- npm (or yarn)
- Java 17

## Setup

1. Clone this repository:

        git clone https://github.com/your-username/leftakias.git

2. Navigate to the project directory:

        cd leftakias

3. Install the dependencies:

        npm install (or yarn install)

4. Run the development server:

        npm run start (or yarn start)

The application should now be running on `http://localhost:3000`.

## Challenges faced

Unfortunately, I was not able to fix a few errors in my code, resulting in the endpoints not working as required. The page should be displaying two tables with data from the backend but there
are request errors in the front end. I would like to continue to work on this project throughout the winter to get it to work along with implementing a search bar and graph.


## Project class structure

- com.example.leftakias
    - controller package
        - CryptocurrencyController class
        - CryptocurrencyData class
        - PortfolioController class
    - entity package
        - Cryptocurrency class
        - Portfolio class
        - PortfolioCryptocurrency class
    - repository package 
        - CryptocurrencyRepository class
        - PortfolioRepository class
    - service package
        - CryptocurrencyService class


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [React](https://reactjs.org/)
- [Node.js](https://nodejs.org/)
- [antd](https://ant.design/)
- [react-toastify](https://github.com/fkhadra/react-toastify)
