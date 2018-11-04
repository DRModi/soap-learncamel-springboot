package com.learncamel.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapFault;

@Component
@Slf4j
public class SoapFaultExceptionProcessor implements Processor {



    public void process(Exchange exchange) throws Exception {

        log.info("***** Inside the SOAP Fault Exception Processor");


        /*log.info("***** Inside the SoapFaultException ");
        SoapFault fault =
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, SoapFault.class);

        log.info("***** Print the SoapFaultException " );
        exchange.getOut().setFault(true);
        exchange.getOut().setBody(fault);*/

       /* SoapFault fault =
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, SoapFault.class);

        fault.getFaultCode()

        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);


        log.info("***** Inside the Fault Exception and Details are:  " + e.getMessage());*/

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        log.error("Actual Exception: " +  exception);
        log.error("Exception message: " + exception.getMessage());
        log.error("Exception cause: " +  exception.getCause());

        exchange.getIn().setHeader("isFault","true");
        exchange.getIn().setBody(exception.getMessage());

        log.info("***** Exit the SOAP Fault Exception Processor");

    }



}
