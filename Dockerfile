FROM openjdk:11

COPY target/avroadapter.jar /target/avroadapter.jar

EXPOSE 8080

CMD ["java","-jar","-Dspring.profiles.active=deploy","/target/avroadapter.jar"]
