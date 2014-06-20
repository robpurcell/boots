/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.events

import com.robbyp.boots.core.services.TransactionService
import com.robbyp.boots.web.domain.TransactionResource
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
class TransactionEventHandler {
    @Autowired
    private TransactionService service

    @Autowired
    private Reactor reactor

    @Selector(value = 'transaction.get', reactor = '@reactor')
    def getTransaction(
        Tuple2<Deferred<ResponseEntity<TransactionResource>, Promise<ResponseEntity<TransactionResource>>>,
            Long> d
    )
    {
        def transaction = service.getTransactionForId(d.t2)
        reactor.notify('response', Event.wrap(Tuple.of(d.t1, transaction)))
    }

}

