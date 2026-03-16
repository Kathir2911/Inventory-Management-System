FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["sh","-c","java -jar target/*.jar"]