FROM maven

WORKDIR /home/app

COPY  . .

EXPOSE 8500

RUN mvn clean install

WORKDIR /root

RUN mkdir spring_file

WORKDIR /root/spring_file

COPY  ./Manual_de_Usuario.pdf .

RUN mkdir tessdata

WORKDIR /root/tessdata

COPY  ./tessdata .

WORKDIR /home/app/target

CMD ["java","-jar","TesinaProjectBank.jar"]