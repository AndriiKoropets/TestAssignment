FROM debian:stretch

# Zulu
ENV ZULU_OPENJDK_VERSION="8=8.17.0.3"

# This affects how strings in Java class files are interpreted. We want UTF-8 and this is the only locale in the
# base image that supports it
ENV LANG="C.UTF-8"

RUN echo "===> Updating debian ....." \
    && apt-get -qq update \
    \
    && echo "===> Installing curl wget netcat python...." \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y \
                apt-transport-https \
                curl \
                gnupg2 \
                wget \
    && echo "Installing Zulu OpenJDK ${ZULU_OPENJDK_VERSION}" \
    && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 0x219BD9C9 \
    && echo "deb http://repos.azulsystems.com/debian stable main" >> /etc/apt/sources.list.d/zulu.list \
    && apt-get -qq update \
    && apt-get -y install zulu-${ZULU_OPENJDK_VERSION} \
    && rm -rf /var/lib/apt/lists/*

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/eis/eis-processor.jar
ADD target/classes/application.yml /usr/share/eis/application.yml
ADD target/classes/log4j2.xml /usr/share/eis/log4j2.xml

ENTRYPOINT ["java", "-Dlog4j.configurationFile=file:/usr/share/eis/log4j2.xml", "-jar", "/usr/share/eis/eis-processor.jar", "--spring.config.location=file:/usr/share/eis/application.yml"]