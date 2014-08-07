/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.joda.money.BigMoney
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
import com.robbyp.boots.web.domain.BalanceResource


class BalanceControllerSpec extends Specification {
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

    def "should return an account balance"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String)
        def mapper = new ObjectMapper()
        List<BalanceResource> balanceResources =
            mapper.readValue(
                entity.body,
                new TypeReference<List<BalanceResource>>() {
                }
            )
        def balanceResource = balanceResources[0]

        then:
        entity.statusCode == HttpStatus.OK
        balanceResource.accountId == testUniqueId
        balanceResource.currency == BigMoney.parse(testBalance).currencyUnit.toString()
        balanceResource.balance == BigMoney.parse(testBalance).amount.toString()

        where:
        id = 1
        testUniqueId = 1
        testBalance = 'GBP 100'
        url = 'http://localhost:8080/api/balances/' + id
    }
}
