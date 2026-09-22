# Credit Card Fraud Detection Backend

A project that predicts whether a credit card transaction is suspicious. 

## What the project demonstrates

- **Java and Spring Boot:** REST API, validation, service layer, exception handling, and persistence
- **PostgreSQL:** stores every transaction and its prediction
- **Python and scikit-learn:** creates reproducible sample data and trains a logistic regression model
- **Docker:** starts the complete system with one command

> This is an educational model trained on synthetic data. It must not be used to approve, block, or investigate real financial transactions.

## Architecture

1. A client sends transaction details to the Spring Boot API.
2. Spring Boot sends the numeric features to the Python model service.
3. The scikit-learn model returns a fraud probability and classification.
4. Spring Boot stores the request and result in PostgreSQL, then returns JSON.

## Project structure

```text
credit-card-fraud-backend/
├── api/                 Spring Boot REST API
├── model-service/       Python training and prediction service
├── docs/                API and GitHub instructions
├── docker-compose.yml   Runs all three services
└── .env.example         Example database configuration
```

## Prerequisites

Install:

- Git
- Docker Desktop

Java, Maven, Python, and PostgreSQL do not need to be installed separately when Docker is used.

## Run the project

From the project folder:

```bash
docker compose up --build
```

The first build can take several minutes. Wait until the logs show that the API has started.

Test the API:

```bash
curl -X POST http://localhost:8080/api/transactions/predict \
  -H "Content-Type: application/json" \
  -d '{"amount":1850,"hour":2,"distanceFromHomeKm":120,"foreignTransaction":true,"onlineTransaction":true,"transactionsLast24h":9}'
```

See [docs/API_EXAMPLES.md](docs/API_EXAMPLES.md) for every endpoint.

Stop the project:

```bash
docker compose down
```

To also delete the local database data:

```bash
docker compose down -v
```

## Model details

`model-service/app/train.py` generates synthetic transactions using a fixed random seed. Fraud likelihood increases with signals such as unusually high amounts, long distance from home, foreign transactions, unusual hours, and many transactions in 24 hours. The service derives an `unusual_hour` feature from the submitted hour. A scikit-learn pipeline standardizes the features and trains logistic regression with balanced class weights.

The model is trained when the Python Docker image is built. This keeps the repository small and makes the process reproducible. For a more advanced version, replace the synthetic data with an anonymized public dataset and compare logistic regression with random forest or gradient boosting.

## License

This project is available under the MIT License.
