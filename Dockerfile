FROM openjdk:17-alpine

ENV PORT=8080 JAVA_OPTS="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true"

COPY ./target/*.jar ./app.jar

EXPOSE ${PORT}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dspring.profiles.active=$SPRING_PROFILES -Dserver.port=${PORT} -jar app.jar"]