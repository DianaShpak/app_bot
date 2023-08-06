FROM maven:3.9.2-eclipse-temurin-20-alpine

ENV SERVICE_IGNORE true
ENV PATH /opt/gatling/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
ENV GATLING_HOME /opt/gatling
ENV Log4jFileName /opt/gatling/results/gatling.log

# create directory for gatling install
RUN mkdir -p  /opt/gatling
RUN pwd
RUN ls -al

COPY . /opt/gatling/src/

WORKDIR /opt/gatling/src

RUN mvn -s install
RUN mvn -s dependency:go-offline gatling:test -Dgatling.simulationClass=simulation.TestSimulation -Dgatling.charting.noReports=true

RUN chmod -R 777 /opt
RUN chmod -R 777 /opt/gatling
