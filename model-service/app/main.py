from pathlib import Path

import joblib
import pandas as pd
from fastapi import FastAPI
from pydantic import BaseModel, ConfigDict, Field

MODEL_PATH = Path(__file__).resolve().parent / "fraud_model.joblib"
artifact = joblib.load(MODEL_PATH)
pipeline = artifact["pipeline"]
features = artifact["features"]

app = FastAPI(title="Fraud Model Service", version=artifact["version"])


class TransactionFeatures(BaseModel):
    amount: float = Field(gt=0)
    hour: int = Field(ge=0, le=23)
    distance_from_home_km: int = Field(ge=0)
    foreign_transaction: bool
    online_transaction: bool
    transactions_last_24h: int = Field(ge=0)


class Prediction(BaseModel):
    model_config = ConfigDict(protected_namespaces=())

    fraud_probability: float
    is_fraud: bool
    model_version: str


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "up", "model_version": artifact["version"]}


@app.post("/predict", response_model=Prediction)
def predict(transaction: TransactionFeatures) -> Prediction:
    values = transaction.model_dump()
    values["unusual_hour"] = int(transaction.hour <= 4 or transaction.hour >= 23)
    row = pd.DataFrame([values])[features]
    probability = float(pipeline.predict_proba(row)[0, 1])
    return Prediction(
        fraud_probability=round(probability, 6),
        is_fraud=probability >= 0.5,
        model_version=artifact["version"],
    )
