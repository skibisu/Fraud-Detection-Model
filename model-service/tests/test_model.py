from app.main import health, predict, TransactionFeatures


def test_health():
    assert health()["status"] == "up"


def test_prediction_is_valid_probability():
    result = predict(TransactionFeatures(
        amount=2500,
        hour=2,
        distance_from_home_km=150,
        foreign_transaction=True,
        online_transaction=True,
        transactions_last_24h=12,
    ))
    assert 0 <= result.fraud_probability <= 1
    assert result.model_version == "student-logreg-v1"


def test_high_risk_transaction_scores_above_low_risk_transaction():
    low_risk = predict(TransactionFeatures(
        amount=20, hour=14, distance_from_home_km=2,
        foreign_transaction=False, online_transaction=False,
        transactions_last_24h=1,
    ))
    high_risk = predict(TransactionFeatures(
        amount=3000, hour=2, distance_from_home_km=200,
        foreign_transaction=True, online_transaction=True,
        transactions_last_24h=15,
    ))
    assert high_risk.fraud_probability > low_risk.fraud_probability
