/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.events

import com.robbyp.boots.core.services.AccountService
import com.robbyp.boots.web.domain.AccountInfoResource
import com.robbyp.boots.web.domain.BalanceResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import reactor.core.Reactor
import reactor.core.composable.Deferred
import reactor.core.composable.Promise
import reactor.event.Event
import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector
import reactor.tuple.Tuple
import reactor.tuple.Tuple2

@Consumer
class AccountEventHandler {

    private static final String RESPONSE_EVENT_KEY = 'response'

    @Autowired
    private AccountService service

    @Autowired
    private Reactor reactor

    @Selector(value = 'account.get', reactor = '@reactor')
    def getAccount(
        Tuple2<Deferred<ResponseEntity<AccountInfoResource>, Promise<ResponseEntity<AccountInfoResource>>>,
            Long> d
    )
    {
        def accountInfo = service.getAccountInfoForId(d.t2)
        reactor.notify(RESPONSE_EVENT_KEY, Event.wrap(Tuple.of(d.t1, accountInfo)))

    }

    @Selector(value = 'account.balance', reactor = '@reactor')
    def getBalance(
        Tuple2<Deferred<ResponseEntity<BalanceResource>, Promise<ResponseEntity<BalanceResource>>>,
            Long> d
    )
    {
        def balance = service.getAccountBalanceForId(d.t2)
        reactor.notify(RESPONSE_EVENT_KEY, Event.wrap(Tuple.of(d.t1, balance)))
    }
}

