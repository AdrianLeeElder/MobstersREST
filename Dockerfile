FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 8050

# update apk repo
RUN echo "http://dl-4.alpinelinux.org/alpine/v3.8/main" >> /etc/apk/repositories && \
    echo "http://dl-4.alpinelinux.org/alpine/v3.8/community" >> /etc/apk/repositories

# install chromedriver
RUN apk update
RUN apk add chromium chromium-chromedriver

ARG JAR_FILE=target/mobsters-api-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} mobsters.jar

# Put Chromedriver into the PATH
ENV CHROMEDRIVER_DIR "/usr/bin/chromedriver"

ENTRYPOINT ["java","-jar","/mobsters.jar"]
