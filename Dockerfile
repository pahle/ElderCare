# Dockerfile
FROM python:3.12.3-slim
COPY requirements.txt /
RUN pip3 install -r /requirements.txt
COPY app.py .
COPY model/ model/
COPY data/ data/
CMD gunicorn --bind :$PORT app:app