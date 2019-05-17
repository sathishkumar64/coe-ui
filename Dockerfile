FROM openjdk:alpine
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 8081 8082
ADD ./target/coe-ui-0.0.1-SNAPSHOT.jar coe-ui-0.0.1-SNAPSHOT.jar
RUN sh -c 'touch /coe-ui-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","coe-ui-0.0.1-SNAPSHOT.jar"]
