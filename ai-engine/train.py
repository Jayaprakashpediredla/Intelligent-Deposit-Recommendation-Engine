"""
Model training script for recommendation engine.
"""

import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
import joblib
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


def load_training_data(filepath):
    """Load training dataset."""
    try:
        data = pd.read_csv(filepath)
        logger.info(f"Loaded {len(data)} training samples")
        return data
    except Exception as e:
        logger.error(f"Error loading data: {str(e)}")
        return None


def preprocess_data(data):
    """Preprocess and feature engineer the data."""
    # Handle missing values
    data = data.fillna(data.mean())

    # Separate features and target
    X = data.drop('deposit_type', axis=1)
    y = data['deposit_type']

    # Standardize features
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    return X_scaled, y, scaler


def train_model(X_train, y_train):
    """Train the recommendation model."""
    try:
        model = RandomForestClassifier(
            n_estimators=100,
            max_depth=10,
            random_state=42,
            n_jobs=-1
        )
        model.fit(X_train, y_train)
        logger.info("Model training completed successfully")
        return model
    except Exception as e:
        logger.error(f"Error training model: {str(e)}")
        return None


def save_model(model, scaler, model_path='models/recommendation_model.pkl'):
    """Save trained model and scaler."""
    try:
        joblib.dump(model, model_path)
        joblib.dump(scaler, model_path.replace('.pkl', '_scaler.pkl'))
        logger.info(f"Model saved to {model_path}")
    except Exception as e:
        logger.error(f"Error saving model: {str(e)}")


def main():
    """Main training pipeline."""
    # Load data
    data = load_training_data('datasets/training_data.csv')
    if data is None:
        return

    # Preprocess
    X_train, y_train, scaler = preprocess_data(data)

    # Train
    model = train_model(X_train, y_train)
    if model is None:
        return

    # Save
    save_model(model, scaler)

    logger.info("Training pipeline completed")


if __name__ == '__main__':
    main()

