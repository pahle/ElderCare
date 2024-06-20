import firebase_admin
from firebase_admin import credentials, firestore, initialize_app
import json
import os
import requests


def load_data_from_json(path):
    with open(path, 'r') as file:
        return json.load(file)

def populate_collection(collection_ref, data):
    batch = db.batch()
    for item in data:
        doc_ref = collection_ref.document()  # Create a new document reference
        batch.set(doc_ref, item)
    batch.commit()
    print(f"Data successfully added to {collection_ref.id} collection.")

if __name__ == "__main__":
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

    # Load data from JSON files
    diseases_data = load_data_from_json('data/db_diseases.json')
    symptoms_data = load_data_from_json('data/db_symptoms.json')

    # Get references to Firestore collections
    diseases_ref = db.collection('diseases')
    symptoms_ref = db.collection('symptoms')

    # Populate Firestore collections
    populate_collection(diseases_ref, diseases_data)
    populate_collection(symptoms_ref, symptoms_data)