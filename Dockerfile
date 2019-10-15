FROM fabric8/java-jboss-openjdk8-jdk
EXPOSE 8769 8770
USER root
ENV LANG en_US.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD ./wjsy-gatetway-zuul.jar .
CMD java -jar wjsy-gatetway-zuul.jar
