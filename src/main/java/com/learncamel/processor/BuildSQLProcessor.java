package com.learncamel.processor;




import com.learncamel.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildSQLProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        log.info("**** Build SQL Processor : START ");

        if(exchange!=null){

            Country country = (Country) exchange.getIn().getBody();

            /*COUNTRY_NAME TEXT,
            COUNTRY_CAPITAL TEXT,
            COUNTRY_ALPHA3CODE TEXT,
            COUNTRY_POPULATION NUMERIC,*/

            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("INSERT INTO COUNTRY (COUNTRY_NAME, COUNTRY_CAPITAL, COUNTRY_ALPHA3CODE, COUNTRY_POPULATION) VALUES ('");
            sqlQuery.append(country.getCountryFullName()+"','"+country.getCountryCapitalName()+"','"+country.getCountryAlpha3Code()+"',"+country.getCountryPopulation()+")");


            log.info("**** Build SQL Processor : Constructed SQL Query is :" + sqlQuery.toString());

            exchange.getIn().setBody(sqlQuery.toString());
            exchange.getIn().setHeader("countryIDFromHeader", country.getCountryAlpha3Code());

        }

        log.info("**** Build SQL Processor : EXIT ");
    }
}