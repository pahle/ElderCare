from flask import Flask, request, jsonify
import json
from tensorflow.keras.models import load_model
import numpy as np

app = Flask(__name__)
model = load_model('model.h5')
sickness = json.load(open('sickness.json'))["data"]


@app.route("/predict", methods=["POST"])
def predict():
  input = request.get_json()
  if input is None:
    return jsonify({"error": "No data provided"}), 400

  try:
    data = np.array(input["data"]).reshape(1, -1)
  except KeyError:
    return jsonify({"error": "Missing key 'data' in request"}), 400
  
  
  prediction = model.predict(data)

  predicted_label_index = np.argmax(prediction)

  return jsonify({"prediction": str(sickness[predicted_label_index]), "accuracy": str(prediction[0][predicted_label_index])})

if __name__ == "__main__":
  app.run(debug=True)
