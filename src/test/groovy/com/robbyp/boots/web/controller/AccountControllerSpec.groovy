/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.robbyp.boots.Application
import com.robbyp.boots.web.domain.AccountInfoResource
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class AccountControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    def context

    void setupSpec() {
        Future future =
            Executors.newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    public ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication.run(Application.class)
                    }
                })
        context = future.get(60, TimeUnit.SECONDS)
    }

    void "should return Greetings"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8080",
                                                                String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == 'Greetings from Boots!'
    }

    void "should return an account"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String.class)
        def mapper = new ObjectMapper()
        def accountResource = mapper.readValue(entity.body, AccountInfoResource.class)

        then:
        entity.statusCode == HttpStatus.OK
        accountResource.uniqueId == uniqueId
        accountResource.name == name
        accountResource.number == number
        accountResource.institution == institution
        accountResource.currency == currency
        accountResource.type == type

        where:
        id || uniqueId || name              || number              || institution || currency || type
        1  || 1        || 'Current Account' || '11-22-33 12345678' || 'HSBC'      || 'GBP'    || 'Current'

        url = 'http://localhost:8080/accounts/' + id
    }

}