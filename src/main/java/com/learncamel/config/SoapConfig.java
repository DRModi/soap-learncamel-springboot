package com.learncamel.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapConfig {


    @Bean(value = "CountryWSTemplate")
    public WebServiceTemplate createWebServiceTemplate(){

        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri("http://localhost:8091/soap-ws/country");
        //template.setDefaultUri("http://localhost:8091/soap-ws"); //Both works since soap-service exposed with /soap-ws/*
        return template;

    }

}
