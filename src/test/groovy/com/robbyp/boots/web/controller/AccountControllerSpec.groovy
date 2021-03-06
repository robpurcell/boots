/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.joda.money.CurrencyUnit
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
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.web.domain.AccountInfoResource


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

    void "should return an account info"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String)
        def mapper = new ObjectMapper()
        List<AccountInfoResource> accountInfoResources =
            mapper.readValue(
                entity.body,
                new TypeReference<List<AccountInfoResource>>() {
                }
            )
        def accountInfoResource = accountInfoResources[0]

        then:
        entity.statusCode == HttpStatus.OK
        assert accountInfoResource.uniqueId == testUniqueId
        assert accountInfoResource.name == testName
        assert accountInfoResource.number == testNumber
        assert accountInfoResource.institution == testInst
        assert accountInfoResource.currency == testCcy
        assert accountInfoResource.type == testType

        where:
        id || testUniqueId || testName          || testNumber          || testInst || testCcy || testType
        1  || 1            || 'Current Account' || '11-22-33 12345678' || 'HSBC'   || 'GBP'   || 'Current'

        url = 'http://localhost:8080/api/accounts/' + id
    }

    void "should return a list of account info resources"() {
        when:
        ResponseEntity<String> entity =
            new RestTemplate().getForEntity(url, String)
        def mapper = new ObjectMapper()
        List<AccountInfoResource> accountInfoResources =
            mapper.readValue(
                entity.body,
                new TypeReference<List<AccountInfoResource>>() {
                }
            )

        then:
        entity.statusCode == HttpStatus.OK

        assert accountInfoResources.size() == 2
        assert accountInfoResources == allAccountInfoResources

        where:
        accountInfoResource = new AccountInfoResource(
            1,
            'Current Account',
            '11-22-33 12345678',
            'HSBC',
            CurrencyUnit.GBP,
            AccountType.CURRENT
        )

        allAccountInfoResources = [accountInfoResource, accountInfoResource]

        url = 'http://localhost:8080/api/accounts/'
    }

}
