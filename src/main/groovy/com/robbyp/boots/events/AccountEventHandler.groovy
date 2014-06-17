/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.events

import com.robbyp.boots.core.services.AccountService
import com.robbyp.boots.web.domain.AccountInfoResource
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler
import com.robbyp.boots.web.domain.BalanceResource
import com.robbyp.boots.web.domain.BalanceResourceAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.composable.Deferred
import reactor.core.composable.Promise
import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector
import reactor.tuple.Tuple2

@Consumer
class AccountEventHandler {
    @Autowired
    AccountService service

    @Autowired
    AccountInfoResourceAssembler accountInfoResourceAssembler

    @Autowired
    BalanceResourceAssembler balanceResourceAssembler

    @Selector(value = 'account.get', reactor = '@reactor')
    def getAccount(
        Tuple2<Deferred<ResponseEntity<AccountInfoResource>, Promise<ResponseEntity<AccountInfoResource>>>,
            Long> d
    )
    {
        def accountInfo = service.getAccountInfoForId(d.t2)
        def accountInfoResource = accountInfoResourceAssembler.instantiateResource(accountInfo)

        d.t1.accept new ResponseEntity<AccountInfoResource>(accountInfoResource, HttpStatus.OK)
    }

    @Selector(value = 'account.balance', reactor = '@reactor')
    def getBalance(
        Tuple2<Deferred<ResponseEntity<BalanceResource>, Promise<ResponseEntity<BalanceResource>>>,
            Long> d
    )
    {
        def balance = service.getAccountBalanceForId(d.t2)
        def balanceResource = balanceResourceAssembler.instantiateResource(balance)

        d.t1.accept new ResponseEntity<BalanceResource>(balanceResource, HttpStatus.OK)
    }
}

