package com.learncamel.domain;

public class Country {

    private String countryFullName;
    private String countryCapitalName;
    private String countryAlpha3Code;
    private long countryPopulation;

    public String getCountryFullName() {
        return countryFullName;
    }

    public void setCountryFullName(String countryFullName) {
        this.countryFullName = countryFullName;
    }

    public String getCountryCapitalName() {
        return countryCapitalName;
    }

    public void setCountryCapitalName(String countryCapitalName) {
        this.countryCapitalName = countryCapitalName;
    }

    public String getCountryAlpha3Code() {
        return countryAlpha3Code;
    }

    public void setCountryAlpha3Code(String countryAlpha3Code) {
        this.countryAlpha3Code = countryAlpha3Code;
    }

    public long getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(long countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryFullName='" + countryFullName + '\'' +
                ", countryCapitalName='" + countryCapitalName + '\'' +
                ", countryAlpha3Code='" + countryAlpha3Code + '\'' +
                ", countryPopulation=" + countryPopulation +
                '}';
    }
}
