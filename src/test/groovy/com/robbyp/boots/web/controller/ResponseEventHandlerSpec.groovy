/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import static com.robbyp.boots.test.TestDataGenerators.anyDate
import static com.robbyp.boots.test.TestDataGenerators.anyString

import com.robbyp.boots.Application
import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.core.domain.Transaction
import com.robbyp.boots.web.domain.AccountInfoResource
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler
import com.robbyp.boots.core.domain.Balance
import com.robbyp.boots.web.domain.BalanceResourceAssembler
import com.robbyp.boots.web.domain.TransactionResourceAssembler
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import reactor.core.Environment
import reactor.core.composable.spec.Promises
import reactor.tuple.Tuple
import spock.lang.Specification

@WebAppConfiguration
@ContextConfiguration(classes = Application)
class ResponseEventHandlerSpec extends Specification {

    @Autowired
    ResponseEventHandler responseEventHandler

    @Autowired
    Environment env

    void "should select the correct assembler"() {
        when:
        def resourceAssembler = responseEventHandler.determineResourceAssembler(resourceClass)

        then:
        resourceAssembler.class == actualAssemblerClass

        where:
        resourceClass || actualAssemblerClass
        Balance       || BalanceResourceAssembler
        AccountInfo   || AccountInfoResourceAssembler
        Transaction   || TransactionResourceAssembler

    }

    void "should select the correct assembler for a list of entities"() {
        given:
        def accountInfo =
            new AccountInfo(
                uniqueId: 1L,
                name: anyString(),
                number: anyString(),
                institution: anyString(),
                currency: CurrencyUnit.GBP,
                type: AccountType.CURRENT,
                openingDate: anyDate(),
                openingBalance: BigMoney.parse('GBP 0')
            )

        def allAccountInfo = [accountInfo, accountInfo]

        when:
        def resourceAssembler = responseEventHandler.determineResourceAssembler(allAccountInfo)

        then:
        resourceAssembler.class == AccountInfoResourceAssembler
    }

    void "should format the response correctly"() {
        given:
        def deferred = Promises.<ResponseEntity<AccountInfoResource>> defer(env)
        def accountInfo =
            new AccountInfo(
                uniqueId: 1L,
                name: anyString(),
                number: anyString(),
                institution: anyString(),
                currency: CurrencyUnit.GBP,
                type: AccountType.CURRENT,
                openingDate: anyDate(),
                openingBalance: BigMoney.parse('GBP 0')
            )
        def expectedAccountInfoResource = new AccountInfoResource(
            accountInfo.uniqueId,
            accountInfo.name,
            accountInfo.number,
            accountInfo.institution,
            accountInfo.currency,
            accountInfo.type
        )

        when:
        responseEventHandler.formatResponse(Tuple.of(deferred, Collections.singletonList(accountInfo)))
        deferred.compose().await()

        then:
        def entity = deferred.compose().get()
        entity.statusCode == HttpStatus.OK

        for (entry in entity.body) {
            assert entry.uniqueId == expectedAccountInfoResource.uniqueId
            assert entry.name == expectedAccountInfoResource.name
            assert entry.number == expectedAccountInfoResource.number
            assert entry.institution == expectedAccountInfoResource.institution
            assert entry.currency == expectedAccountInfoResource.currency
            assert entry.type == expectedAccountInfoResource.type
        }
    }

}
