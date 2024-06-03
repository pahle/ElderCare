from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
import numpy as np

app = Flask(__name__)
model = load_model('model.h5')

@app.route("/predict", methods=["POST"])
def predict():
  # Get data from request (assuming JSON)
  data = request.get_json()
  if data is None:
    return jsonify({"error": "No data provided"}), 400

  # Extract array from data (assuming key 'data')
  try:
    array = data["data"]
  except KeyError:
    return jsonify({"error": "Missing key 'data' in request"}), 400
  
  sickness= [
        "Fungal infection",
        "Allergy",
        "GERD",
        "Chronic cholestasis",
        "Drug Reaction",
        "Peptic ulcer disease",
        "AIDS",
        "Diabetes",
        "Gastroenteritis",
        "Bronchial Asthma",
        "Hypertension",
        "Migraine",
        "Cervical spondylosis",
        "Paralysis (brain hemorrhage)",
        "Jaundice",
        "Malaria",
        "Chicken pox",
        "Dengue",
        "Typhoid",
        "Hepatitis A",
        "Hepatitis B",
        "Hepatitis C",
        "Hepatitis D",
        "Hepatitis E",
        "Alcoholic hepatitis",
        "Tuberculosis",
        "Common Cold",
        "Pneumonia",
        "Dimorphic hemorrhoids (piles)",
        "Heart attack",
        "Varicose veins",
        "Hypothyroidism",
        "Hyperthyroidism",
        "Hypoglycemia",
        "Osteoarthritis",
        "Arthritis",
        "Paroxysmal Positional Vertigo",
        "Acne",
        "Urinary tract infection",
        "Psoriasis",
        "Impetigo",
        "Fungal infection"
    ]

  sample_input = np.array(array)

  sample_input = sample_input.reshape(1, -1)

    # Predict using your model_script (assuming predict function defined there)
  prediction = model.predict(sample_input)  # Pass model and data

  # find the highest value in the prediction array
  highest = np.argmax(prediction[0])

  second_highest = np.argsort(prediction[0])[-2]

  # print(sickness[highest])
  print(second_highest)
  print(prediction[0][second_highest])

  # Return prediction as JSON
  # return jsonify({"prediction": prediction[0].tolist()})
  return jsonify({"first_prediction": sickness[highest], "first_accuracy": str(prediction[0][highest]), "second_prediction": sickness[second_highest], "second_accuracy": str(prediction[0][second_highest])})

if __name__ == "__main__":
  app.run(debug=True)
