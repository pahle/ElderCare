# Dockerfile
FROM python:3.12.3-slim
COPY requirements.txt /
RUN pip3 install -r /requirements.txt
COPY app.py .
COPY model/ model/
COPY data/ data/
ENV FIREBASE_CRED_URL=https://storage.googleapis.com/eldercare-firestore-cred/credentials.json
CMD gunicorn --bind :$PORT app:app