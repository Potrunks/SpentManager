FROM node:18.1.0-bullseye-slim
RUN apt-get update
RUN apt-get install -y default-jre