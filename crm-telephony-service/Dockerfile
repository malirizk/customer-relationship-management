FROM adoptopenjdk/openjdk21:alpine-slim
WORKDIR /app
COPY target/crm-telephony-service.jar /app
EXPOSE 8090
CMD ["java", "-jar", "crm-telephony-service.jar"]
