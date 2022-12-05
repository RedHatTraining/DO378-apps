import time
import random
from datetime import date, timedelta
from flask import Flask

app = Flask(__name__)


# Routes

@app.route("/")
def get_home():
    return {
        "name": "Prices Service for DO378",
        "routes": [
            { "name": "Home", "path": "/", "method": "GET"},
            { "name": "Product prices history", "path": "/history/<product_id>", "method": "GET"},
        ]
    }

@app.route("/history/<product_id>")
def get_history(product_id: int):
    time.sleep(2)
    return {
        "product_id": product_id,
        "prices": simulate_prices_history(product_id)
    }


# Helpers

def simulate_prices_history(product_id: int):
    deltas = range(100)
    random.seed(product_id)
    base_price = random.random() * 100
    return [ generate_price_for_past_day(base_price, days_delta) for days_delta in deltas ]


def generate_price_for_past_day(base_price: float, days_delta: int):
    when = date.today() - timedelta(days=days_delta)

    return {
        "date": when.isoformat(),
        "price": base_price + random.random()
    }
