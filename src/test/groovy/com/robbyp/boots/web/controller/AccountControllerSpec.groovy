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
                    ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication.run(Application)
                    }
                }
            )
        context = future.get(60, TimeUnit.SECONDS)
    }

    void "should return an account"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String)
        def mapper = new ObjectMapper()
        def accountInfoResource = mapper.readValue(entity.body, AccountInfoResource)

        then:
        entity.statusCode == HttpStatus.OK
        accountInfoResource.with {
            uniqueId == testUniqueId
            name == testName
            number == testNumber
            institution == testInstitution
            currency == testCurrency
            type == testType
        }

        where:
        id || testUniqueId || testName          || testNumber || testInstitution || testCurrency || testType
        1  || 1            || 'Current Account' || '11-22-33' || 'HSBC'          || 'GBP'        || 'Current'

        url = 'http://localhost:8080/accounts/' + id
    }

}