FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine

RUN addgroup -S weasylearn && adduser -S weasy -G weasylearn

USER weasy:weasylearn

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wl.jar
ENTRYPOINT ["java","-jar","/wl.jar"]
