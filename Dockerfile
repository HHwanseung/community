FROM openjdk:11-jdk
COPY apidocs /home/ec2-user/community
ENTRYPOINT ["java","-jar","/app.jar"]