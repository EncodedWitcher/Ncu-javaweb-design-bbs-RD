FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:17

LABEL authors="byssted@email.ncu.edu.cn"

WORKDIR /opt/app

COPY bbs-rd-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]