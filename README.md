# ConvertCurrency
This project is used for to convert currency amount based on country code.

Swagger is avaiable for the below url:
https://localhost:8090/swagger-ui/index.html

Convertcurrency microservice is registered in the eureka server

 Url of Eureka Server is : http://localhost:8761</br>
 
 Circuit breaker is implemented by using Resilence4j library.</br>
 OpenFiegn Client is used for to call currency conversion factor microservice</br>
 
Docker image pull: docker pull microserviceapps/convertcurrency</br>
Docker run: docker run -p 8090:8090 microserviceapps/convertcurrency:1.0.0
