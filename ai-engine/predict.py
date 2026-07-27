"""
Prediction module for recommendation engine.
"""

import joblib
import numpy as np
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


class Predictor:
    """Predictor class for making recommendations."""

    def __init__(self, model_path='models/recommendation_model.pkl'):
        """Initialize predictor with trained model."""
        try:
            self.model = joblib.load(model_path)
            self.scaler = joblib.load(model_path.replace('.pkl', '_scaler.pkl'))
            logger.info("Model loaded successfully")
        except FileNotFoundError:
            logger.error(f"Model file not found: {model_path}")
            self.model = None
            self.scaler = None

    def predict(self, features):
        """
        Make prediction for given features.

        Args:
            features: Array or list of feature values

        Returns:
            Predicted deposit type and confidence score
        """
        if self.model is None:
            raise Exception("Model not loaded")

        try:
            # Ensure features is numpy array
            features_array = np.array(features).reshape(1, -1)

            # Scale features
            features_scaled = self.scaler.transform(features_array)

            # Make prediction
            prediction = self.model.predict(features_scaled)[0]
            probabilities = self.model.predict_proba(features_scaled)[0]
            confidence = np.max(probabilities)

            return {
                'recommendation': prediction,
                'confidence': float(confidence),
                'probabilities': {
                    str(i): float(prob)
                    for i, prob in enumerate(probabilities)
                }
            }
        except Exception as e:
            logger.error(f"Error making prediction: {str(e)}")
            raise

    def batch_predict(self, features_list):
        """
        Make predictions for multiple samples.

        Args:
            features_list: List of feature arrays

        Returns:
            List of predictions
        """
        predictions = []
        for features in features_list:
            predictions.append(self.predict(features))
        return predictions


def main():
    """Example usage."""
    predictor = Predictor()

    # Example feature vector
    features = [25, 50000, 10000, 0.05, 12]  # age, income, savings, interest_rate, months

    result = predictor.predict(features)
    print(f"Recommendation: {result}")


if __name__ == '__main__':
    main()

