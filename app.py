from flask import Flask, request, jsonify
import json
from tensorflow.keras.models import load_model
import numpy as np

# Initialize the Flask application
app = Flask(__name__)

# Load the model
model = load_model('model.h5')

# Load the disease data
disease = json.load(open('disease.json'))["data"]

@app.route("/")
def get_index():
  return get_index_handler()

@app.route("/predict", methods=["POST"])
def post_predict():
  return post_predict_handler()

def get_index_handler():
  return jsonify({
        "status": "success",
        "code": 200,
        "message": "Successfully connected to the API",
    }), 200
  
def post_predict_handler():
  request_data = request.json
  print(request_data)
    
  if "data" not in request_data:
    return jsonify({
        "status": "fail",
        "code": 400,
        "message": "Data is missing in the request body",
    }), 400

  data = np.array(request_data["data"]).reshape(1, -1)
    
  prediction = model.predict(data)

  predicted_label_index = np.argmax(prediction)

  return jsonify({
    "status": "success",
    "code": 200,
    "message": "Prediction successful",
    "data": {
      "prediction": disease[predicted_label_index],
      "probability": str(int(prediction[0][predicted_label_index] * 100)) + "%"
    }
  }), 200
  
if __name__ == "__main__":
  app.run(debug=True)
