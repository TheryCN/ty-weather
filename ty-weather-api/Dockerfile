# docker login
# docker build -t tcharass/tyweather . --build-arg appVersion=0.0.1-SNAPSHOT
# docker run -p 4000:8080 tcharass/tyweather

FROM openjdk:7-alpine

# Install JDK 8
RUN apk add --no-cache openjdk8

ARG appVersion=0.0.1-SNAPSHOT
ADD target/ty-weather.jar /opt/ty-weather/lib/ty-weather.jar

CMD ["/usr/bin/java","-jar", "/opt/ty-weather/lib/ty-weather.jar"]
EXPOSE 8080