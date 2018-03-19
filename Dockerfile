FROM java:8-alpine
MAINTAINER Oliver Marshall <olivershawmarshall@gmail.com>

ADD target/uberjar/alton-towers.jar /study-timer/app.jar

CMD ["java", "-jar", "/study-timer/app.jar"]