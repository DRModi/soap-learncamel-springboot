# SOAP-LearnCamel-SpringBoot Application!
----------------------------------------------


### Demonstrate following using camel-components/springboot:

  ##### (1) Create processor to select country alpha2code to call `soap-country-api` webservice.
  ##### (2) Retrieve messages from country soap web service.
  ##### (3) Convert/UnMarshall soap response using xStream Data Format - from soap response message to Java Object and store it in database.
  ##### (4) Exception Handling, Send email alerts for data validation failure.
  ##### (5) Integrating spring actuator for component health check using route.


----------------------------------------

#### SOAP WEB Service `soap-country-api` 

-----------------------------------------
#### 1. Clone `soap-learncamel-springboot` repository to local machine.
> - It contains jar which expose soap country api (developed using spring boot)
>     [Github URL](https://github.com/DRModi/springboot-soap-country-api)
> - Browse to cloned folder and start country api service using below command.

```
$ java -jar country-soap-api/springboot-soap-country-api-0.0.1-SNAPSHOT.jar
```

#### 2. Service will run on port 8091
> - [Service WSDL:](http://localhost:8091/soap-ws/country.wsdl)
> - [Service URL:](http://localhost:8091/soap-ws)

#### 3. Additional Information - XSD, Request and Response, Exception! 
> - [Github URL](https://github.com/DRModi/springboot-soap-country-api)


--------------------------------------



### Application Overview:

<img width="721" alt="screen shot 2018-11-04 at 12 55 04 pm" src="https://user-images.githubusercontent.com/30615418/47967915-fd72a080-e030-11e8-9d0e-92b3c6776699.png">
