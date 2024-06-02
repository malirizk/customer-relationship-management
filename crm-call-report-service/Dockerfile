FROM adoptopenjdk/openjdk21:alpine-slim
WORKDIR /app
COPY target/crm-call-report-service.jar /app
EXPOSE 8090
CMD ["java", "-jar", "crm-call-report-service.jar"]
