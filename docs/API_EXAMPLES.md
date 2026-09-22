# API examples

The Spring Boot API is available at `http://localhost:8080`.

## Predict fraud and save the transaction

```bash
curl -X POST http://localhost:8080/api/transactions/predict \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1850.00,
    "hour": 2,
    "distanceFromHomeKm": 120,
    "foreignTransaction": true,
    "onlineTransaction": true,
    "transactionsLast24h": 9
  }'
```

Example response:

```json
{
  "id": 1,
  "amount": 1850.0,
  "hour": 2,
  "distanceFromHomeKm": 120,
  "foreignTransaction": true,
  "onlineTransaction": true,
  "transactionsLast24h": 9,
  "fraudProbability": 0.997412,
  "fraud": true,
  "modelVersion": "student-logreg-v1",
  "createdAt": "2026-01-01T12:00:00Z"
}
```

## List saved transactions

```bash
curl http://localhost:8080/api/transactions
```

## Get one transaction

```bash
curl http://localhost:8080/api/transactions/1
```

## Health checks

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8000/health
```

