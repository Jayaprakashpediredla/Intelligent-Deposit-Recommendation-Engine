"""
Main Flask application for AI recommendation engine.
"""

from flask import Flask, jsonify, request
from flask_cors import CORS
from dotenv import load_dotenv
import logging

from app.models.recommendation_engine import RecommendationEngine

# Load environment variables
load_dotenv()

# Initialize Flask app
app = Flask(__name__)
CORS(app)

# Setup logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Initialize recommendation engine
recommendation_engine = RecommendationEngine()


@app.route('/health', methods=['GET'])
def health_check():
    """Health check endpoint."""
    return jsonify({
        'status': 'healthy',
        'service': 'recommendation-engine'
    }), 200


@app.route('/api/v1/recommendations', methods=['POST'])
def get_recommendations():
    """
    Get deposit recommendations based on customer profile and transaction history.
    """
    try:
        data = request.get_json()

        # Validate input
        if not data or 'customer_id' not in data:
            return jsonify({'error': 'Missing customer_id'}), 400

        # Generate recommendations
        recommendations = recommendation_engine.recommend(data)

        return jsonify({
            'success': True,
            'recommendations': recommendations
        }), 200

    except Exception as e:
        logger.error(f"Error generating recommendations: {str(e)}")
        return jsonify({'error': str(e)}), 500


@app.route('/api/v1/model/train', methods=['POST'])
def train_model():
    """Trigger model retraining."""
    try:
        recommendation_engine.train()
        return jsonify({
            'success': True,
            'message': 'Model training completed'
        }), 200
    except Exception as e:
        logger.error(f"Error training model: {str(e)}")
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)

