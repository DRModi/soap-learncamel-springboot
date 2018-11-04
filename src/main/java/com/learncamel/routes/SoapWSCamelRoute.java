package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.processor.SoapFaultExceptionProcessor;
import com.learncamel.processor.BuildSQLProcessor;
import com.learncamel.processor.RequestXMLBuildProcessor;
import com.learncamel.domain.Country;
import com.learncamel.exception.DataException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.model.dataformat.XStreamDataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.soap.client.SoapFaultClientException;


import javax.sql.DataSource;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z001qgd on 1/3/18.
 */
@Component
public class SoapWSCamelRoute extends RouteBuilder{

    @Autowired
    Environment environment;

    @Qualifier("myDataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    MailProcessor mailProcessor;

    @Autowired
    RequestXMLBuildProcessor requestXMLBuildProcessor;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;

    @Autowired
    SoapFaultExceptionProcessor soapFaultExceptionProcessor;


    Predicate isNotMockProfile = header("env").isNotEqualTo("mock");



    @Override
    public void configure() throws Exception {

        Map<String, String> reference = new HashMap<>();
        reference.put("country",Country.class.getName());

        XStreamDataFormat xStreamDataFormat = new XStreamDataFormat();
        xStreamDataFormat.setAliases(reference);
        xStreamDataFormat.setPermissions(Country.class.getName());

        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);


        onException(DataException.class,RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);

        /* I/O Network connection Error - If server is down
        onException(ServiceException.class, ConnectException.class).handled(true).log(LoggingLevel.ERROR, "SOAP - WS - Country WEB Service - ConnectionError in the route ${body}")
                .process(soapIOExceptionProcessor).process(mailProcessor);
        */


        /*
        * SoapFaultClientException - in case unexpected input
        * WebserviceTransportException - Wrong URL
        * ConnectException - When server is down
        * */


        onException(SoapFaultClientException.class, WebServiceTransportException.class, ConnectException.class).maximumRedeliveries(0).handled(true).log(LoggingLevel.ERROR, "SOAP FAULT EXCEPTION ! -  ${body}")
                .process(soapFaultExceptionProcessor).process(mailProcessor);


        /*from("{{fromRoute}}")
                .log("Current Environment is "+ environment.getProperty("message"))
                .process(requestXMLBuildProcessor)
                .to("{{toRoute}}")
                .log("The SOAP service Response is ${body}")
                .transform().xpath("/ns2:getCountryResponse/ns2:country", new Namespaces("ns2", "http://drmodi.learn.com/model/country"))
                .log("Output country object : ${body}")
                .unmarshal(xStreamDataFormat)
                .log("UnMarshall Output : ${body}")
                .process(buildSQLProcessor)
                .to("{{tojdbcRoute}}");
            */




        from("{{fromRoute}}").routeId("mainSOAPWS-Route")
                .log("Current Environment is "+ environment.getProperty("message"))

                .choice() // Content based EIP
                  .when(isNotMockProfile) // not mock check
                      .process(requestXMLBuildProcessor)
                .end()

                .to("{{toRoute}}")
                .log("The SOAP service Response is ${body}")
                .transform().xpath("/ns2:getCountryResponse/ns2:country", new Namespaces("ns2", "http://drmodi.learn.com/model/country"))
                .log("Output country object : ${body}")
                .unmarshal(xStreamDataFormat)
                .log("UnMarshall Output : ${body}")

                .choice() // Content based EIP
                    .when(isNotMockProfile) // not mock check
                        .process(buildSQLProcessor)
                        .to("{{tojdbcRoute}}")
                .end();


        }
}
