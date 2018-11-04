package com.learncamel.exception;

import org.springframework.ws.soap.client.SoapFaultClientException;

import java.net.ConnectException;

/**
 * Created by z001qgd on 1/21/18.
 */
public class ServiceException extends ConnectException {

    public ServiceException(String message) {
        super( message);
    }
}
