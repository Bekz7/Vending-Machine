FROM jdk-11.0.6_10-alpine
VOLUME /tmp
ENV JAVA_OPTS=""

ADD build/libs/vending-machine-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
