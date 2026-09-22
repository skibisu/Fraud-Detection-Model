from pathlib import Path

import joblib
import numpy as np
import pandas as pd
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report, roc_auc_score
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler

FEATURES = [
    "amount",
    "hour",
    "distance_from_home_km",
    "foreign_transaction",
    "online_transaction",
    "transactions_last_24h",
    "unusual_hour",
]
MODEL_PATH = Path(__file__).resolve().parent / "fraud_model.joblib"
RANDOM_SEED = 42


def generate_data(rows: int = 12_000) -> pd.DataFrame:
    """Create reproducible, intentionally simplified transaction data."""
    rng = np.random.default_rng(RANDOM_SEED)
    amount = np.clip(rng.lognormal(mean=4.2, sigma=1.0, size=rows), 1, 5_000)
    hour = rng.integers(0, 24, rows)
    distance = np.clip(rng.gamma(shape=2.0, scale=12.0, size=rows), 0, 300)
    foreign = rng.binomial(1, 0.12, rows)
    online = rng.binomial(1, 0.55, rows)
    tx_count = np.clip(rng.poisson(3.0, rows), 0, 25)

    risky_hour = ((hour <= 4) | (hour >= 23)).astype(int)
    logit = (
        -6.5
        + 0.0025 * amount
        + 0.025 * distance
        + 1.8 * foreign
        + 0.55 * online
        + 0.25 * tx_count
        + 1.4 * risky_hour
    )
    probability = 1 / (1 + np.exp(-logit))
    fraud = rng.binomial(1, probability)

    return pd.DataFrame({
        "amount": amount,
        "hour": hour,
        "distance_from_home_km": distance,
        "foreign_transaction": foreign,
        "online_transaction": online,
        "transactions_last_24h": tx_count,
        "unusual_hour": risky_hour,
        "is_fraud": fraud,
    })


def train_and_save() -> None:
    data = generate_data()
    x_train, x_test, y_train, y_test = train_test_split(
        data[FEATURES], data["is_fraud"], test_size=0.2,
        random_state=RANDOM_SEED, stratify=data["is_fraud"]
    )
    pipeline = Pipeline([
        ("scale", StandardScaler()),
        ("model", LogisticRegression(class_weight="balanced", max_iter=1_000,
                                      random_state=RANDOM_SEED)),
    ])
    pipeline.fit(x_train, y_train)

    predictions = pipeline.predict(x_test)
    probabilities = pipeline.predict_proba(x_test)[:, 1]
    print(classification_report(y_test, predictions, digits=3))
    print(f"ROC-AUC: {roc_auc_score(y_test, probabilities):.3f}")

    artifact = {"pipeline": pipeline, "features": FEATURES, "version": "student-logreg-v1"}
    joblib.dump(artifact, MODEL_PATH)
    print(f"Saved model to {MODEL_PATH}")


if __name__ == "__main__":
    train_and_save()
