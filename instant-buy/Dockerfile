FROM openjdk:11.0.7-jre
MAINTAINER muzili<chuan_job@163.com>
ENV APP_PORT=10008
EXPOSE ${APP_PORT}
ARG JAR_FILE
WORKDIR /opt
COPY target/${JAR_FILE} /opt/app.jar
CMD ["java", "-version"]
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
