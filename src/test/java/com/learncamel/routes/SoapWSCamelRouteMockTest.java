package com.learncamel.routes;

import com.learncamel.domain.Country;
import com.learncamel.exception.DataException;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by z001qgd on 1/13/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(true)
public class SoapWSCamelRouteMockTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new SoapWSCamelRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

    public String getCountryRequest = "<getCountryRequest xmlns=\"http://drmodi.learn.com/model/country\">\n" +
            "        <countryCode>in</countryCode>\n" +
            "    </getCountryRequest>";

    public String getCountryExceptionRequest = "<getCountryRequest xmlns=\"http://drmodi.learn.com/model/country\">\n" +
            "        <countryCode>ind</countryCode>\n" +
            "    </getCountryRequest>";

    //public String webServiceURI = "http://localhost:8091/soap-ws";


    @Test
    public void getCountryByAlpha2Code(){

        Country country = (Country) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"), getCountryRequest, "env", "mock");
        //Country country = (Country) producerTemplate.requestBodyAndHeader(webServiceURI, getCountryRequest, "env", "mock");

        assertTrue("india".equalsIgnoreCase(country.getCountryFullName()) );
    }


    @Ignore
    @Test
    public void getCountryExceptionByAlpha2Code(){

        String exceptionString = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"), getCountryExceptionRequest, "env", "mock");
        System.out.println("**** SOAP Fault Exception : " + exceptionString);
        assertEquals("ERROR - NOT FOUND - Possible Country alpha2code values are : {in, us or ch}. Please use according country alpha2code!",exceptionString);

    }


    @Ignore
    @Test
    public void getCountryExceptionServiceDownByAlpha2Code(){

        String exceptionString = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"), getCountryExceptionRequest, "env", "mock");
        System.out.println("**** SOAP Fault Exception : " + exceptionString);
        assertEquals("I/O error: Connection refused (Connection refused); nested exception is java.net.ConnectException: Connection refused (Connection refused)",exceptionString);

    }

    @Ignore
    @Test
    public void getCountryExceptionWrongURLByAlpha2Code(){
        //404 - NOT FOUND
        String exceptionString = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"), getCountryExceptionRequest, "env", "mock");
        System.out.println("**** SOAP Fault Exception : " + exceptionString);
        assertEquals(" [404]",exceptionString);

    }
}
