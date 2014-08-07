/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
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

import com.robbyp.boots.Application
import com.robbyp.boots.web.domain.TransactionResource


class TransactionControllerSpec extends Specification {
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

    void "should return a transaction"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String)
        def mapper = new ObjectMapper()
        List<TransactionResource> transactionResources =
            mapper.readValue(
                entity.body,
                new TypeReference<List<TransactionResource>>() {
                }
            )
        def transactionResource = transactionResources[0]

        then:
        entity.statusCode == HttpStatus.OK
        assert transactionResource.uniqueId == testUniqueId
        assert transactionResource.quantity == testQuantity
        assert transactionResource.price == testPrice
        assert transactionResource.date == testDate
        assert transactionResource.description == testDescription
        assert transactionResource.type == testType

        where:
        id || testUniqueId || testQuantity || testPrice || testType          || testDescription
        1  || 1            || '1'          || 'USD 100' || 'transactionType' || 'description'

        url = 'http://localhost:8080/api/transactions/' + id
        testDate = new LocalDate().toString()
    }

}
