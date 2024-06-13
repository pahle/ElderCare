import os
from flask import Flask, request, jsonify
import json
from tensorflow.keras.models import load_model
import numpy as np

# Initialize the Flask application
app = Flask(__name__)

# Load the model
model = load_model('model.h5')

# Load the data
list_diseases = json.load(open('list_diseases.json'))
db_diseases = json.load(open('db_diseases.json'))
symptoms = json.load(open('list_symptoms.json'))


@app.route("/")
def get_index_handler():
  return jsonify({
        "status": "success",
        "code": 200,
        "message": "Successfully connected to the API",
    }), 200

@app.route("/predict", methods=["POST"])
def post_predict_handler():
  data = request.json
    
  if "data" not in data:
    return jsonify({
        "status": "fail",
        "code": 400,
        "message": "Data is missing in the request body",
    }), 400
    
  data_to_array = []
  for symptom in symptoms:
    if symptom in data["data"]:
      data_to_array.append(1)
    else:
      data_to_array.append(0)

  array_reshape = np.array(data_to_array).reshape(1, -1)
  
  prediction = model.predict(array_reshape)
  
  predicted_disease = list_diseases[np.argmax(prediction)]
  
  print(predicted_disease)
  
  get_translated_index = 0
  
  for i in range(len(db_diseases)):
    if db_diseases[i]["disease"] == predicted_disease:
      get_translated_index = i
      
  detail_disease = db_diseases[get_translated_index]
    
  return jsonify({
    "status": "success",
    "code": 200,
    "message": "Prediction successful",
    "prediction": detail_disease
  }), 200

if __name__ == "__main__":
  app.run(
    host='0.0.0.0',
    port=int(os.environ.get('PORT', 8080))
  )
