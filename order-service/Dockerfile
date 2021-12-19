# When increasing the Java version, it is important to note that the containers should be built with Linux Alpine if possible.
# This saves a lot of memory.
FROM openjdk:8u171-jre-alpine
CMD java ${JAVA_OPTS} -jar order-service-1.0.0-SNAPSHOT.jar
COPY build/libs/order-service-1.0.0-SNAPSHOT.jar .