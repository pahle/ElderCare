# Disease Prediction API
This Flask application provides an API for disease prediction based on symptoms. It integrates Firebase Firestore for data storage and uses a TensorFlow Keras model for predicting diseases.




## Features
- Disease Retrieval: Fetch a list of diseases from Firestore.
- Symptom Retrieval: Fetch symptoms, optionally filtered by category.
- Disease Prediction: Predict diseases based on a list of symptoms provided by the user.
## Prerequisites
Before you can run this application, you will need:

- Python 3.8 or higher
- Firebase account and a Firestore database
- Model file (model.h5) placed in the model/ directory
- Symptoms and diseases data in JSON format placed in the data/ directory
## Installation

Clone the repository
```bash
git clone -b main https://github.com/pahle/ElderCare.git
```

Install the required Python packages
```
pip3 install -r requirements.txt
```

Set up your environment variables on `.env`
```
FIREBASE_CRED_URL: URL to fetch Firebase credentials.
```





## Usage

Populate Firestore with initial data for the first time
```bash
  python3 populate_firestore.py
```

Start the server
```bash
  flask --app app.py --debug run
```

## API Reference

* [Postman API Documentation](https://documenter.getpostman.com/view/20222142/2sA3XPChjx "more info")

