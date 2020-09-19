FROM openjdk:11-alpine

RUN addgroup -S weasylearn && adduser -S weasy -G weasylearn

USER weasy:weasylearn

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wl.jar
ENTRYPOINT ["java","-jar","/wl.jar"]
