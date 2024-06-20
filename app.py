import os
import requests
from flask import Flask, request, jsonify
import json
from tensorflow.keras.models import load_model
import numpy as np
from firebase_admin import credentials, firestore, initialize_app
from dotenv import load_dotenv

load_dotenv()

# Initialize the Flask application
app = Flask(__name__)

# Fetch the credentials from the URL
cred_url = os.getenv('FIREBASE_CRED_URL')
if not cred_url:
    raise Exception('FIREBASE_CRED_URL environment variable not set')

# Fetch the credentials from the URL
cred_response = requests.get(cred_url)
if cred_response.status_code != 200:
    raise Exception('Failed to fetch Firebase credentials')

cred_data = cred_response.json()

# Initialize Firestore DB
cred = credentials.Certificate(cred_data)
default_app = initialize_app(cred)
db = firestore.client()

# Load the model
model = load_model('model/model.h5')

# Load the data
list_diseases = json.load(open('data/list_diseases.json'))
list_symptoms = json.load(open('data/list_symptoms.json'))

# Get Firestore Disease Collection
get_diseases = db.collection('diseases').stream()
db_diseases = []
for doc in get_diseases:
  db_diseases.append(doc.to_dict())
  
# Get Firestore Symptoms Collection
get_symptoms = db.collection('symptoms').stream()
db_symptoms = []
for doc in get_symptoms:
  db_symptoms.append(doc.to_dict())

@app.route("/")
def get_index_handler():
  return jsonify({
        "status": "success",
        "code": 200,
        "message": "Successfully connected to the API",
    }), 200
  
@app.route("/diseases", methods=["GET"])
def get_diseases_handler():
  return jsonify({
    "status": "success",
    "code": 200,
    "message": "Diseases retrieved successfully",
    "diseases": db_diseases
  }), 200
 
@app.route("/symptoms", methods=["GET"])
def get_symptoms_handler():
  
  if request.args.get("category"):
    category = request.args.get("category")
    filtered_symptoms = []
    for doc in db_symptoms:
      if str(doc["category"]).lower() == str(category).lower():
        filtered_symptoms.append(doc)
    return jsonify({
      "status": "success",
      "code": 200,
      "message": "Symptoms retrieved successfully",
      "symptoms": filtered_symptoms
    }), 200
  
  return jsonify({
    "status": "success",
    "code": 200,
    "message": "Symptoms retrieved successfully",
    "symptoms": db_symptoms
  }), 200
  
@app.route("/symptoms", methods=["POST"])
def post_symptoms_category_handler():
  data = request.json
  
  if "category" not in data:
    return jsonify({
        "status": "fail",
        "code": 400,
        "message": "Category is missing in the request body",
    }), 400
  
  filtered_symptoms = []
  for category in data["category"]:
    filtered_symptoms.append({"category": category, "symptoms": []})
    for doc in db_symptoms:
      if str(doc["category"]).lower() == str(category).lower():
        filtered_symptoms[-1]["symptoms"].append(doc)
    
  return jsonify({
    "status": "success",
    "code": 200,
    "message": "Symptoms retrieved successfully",
    "categories": filtered_symptoms
  }), 200  

@app.route("/predict", methods=["POST"])
def post_predict_handler():
  data = request.json
    
  if "symptoms" not in data:
    return jsonify({
        "status": "fail",
        "code": 400,
        "message": "Data is missing in the request body",
    }), 400
    
  data_to_array = []
  for symptom in list_symptoms:
    if symptom in data["symptoms"]:
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
    "prediction": detail_disease,
    "probability": str(int(np.max(prediction * 100))) + "%"
  }), 200

if __name__ == "__main__":
  app.run(
    host='0.0.0.0',
    port=int(os.environ.get('PORT', 8080))
  )
