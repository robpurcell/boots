/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.robbyp.boots.Application
import com.robbyp.boots.web.domain.TransactionResource
import org.joda.time.LocalDate
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

class TransactionControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

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

    void "should return a transaction"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String.class)
        def mapper = new ObjectMapper()
        def transactionResource = mapper.readValue(entity.body, TransactionResource.class)

        then:
        entity.statusCode == HttpStatus.OK
        transactionResource.uniqueId == uniqueId
        transactionResource.quantity == quantity
        transactionResource.price == price
        transactionResource.date == date
        transactionResource.description == description
        transactionResource.type == type

        where:
        id | uniqueId | quantity | price     | type              | description
        1  | 1        | '1'      | 'USD 100' | 'transactionType' | 'description'

        url = 'http://localhost:8080/transactions/' + id
        date = new LocalDate().toString()
    }

}