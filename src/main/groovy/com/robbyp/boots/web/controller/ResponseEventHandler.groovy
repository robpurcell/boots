/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.Transaction
import com.robbyp.boots.core.services.AccountService
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler
import com.robbyp.boots.web.domain.Balance
import com.robbyp.boots.web.domain.BalanceResourceAssembler
import com.robbyp.boots.web.domain.TransactionResourceAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.composable.Deferred
import reactor.core.composable.Promise
import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector
import reactor.tuple.Tuple2

@Consumer
class ResponseEventHandler {
    @Autowired
    AccountService service

    @Autowired
    AccountInfoResourceAssembler accountInfoResourceAssembler

    @Autowired
    BalanceResourceAssembler balanceResourceAssembler

    @Autowired
    TransactionResourceAssembler transactionResourceAssembler

    /**
     * Generically format a response with the outcome from a service method
     *
     * S is the type of the response from the service method
     * R is the type of the corresponding resource to be returned
     *
     * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport
     * @param data Tuple of the deferred response to complete and the data to complete it with
     */
    @Selector(value = 'response', reactor = '@reactor')
    <R, S> void formatResponse(
        Tuple2<Deferred<ResponseEntity<R>, Promise<ResponseEntity<R>>>, S> data
    )
    {
        def resourceAssembler = determineResourceAssembler(data.t2.class)
        R resource = resourceAssembler.instantiateResource(data.t2)

        data.t1.accept new ResponseEntity<R>(resource, HttpStatus.OK)
    }

    def determineResourceAssembler(Class aClass) {
        switch (aClass) {
            case Balance:
                return balanceResourceAssembler
            case AccountInfo:
                return accountInfoResourceAssembler
            case Transaction:
                return transactionResourceAssembler
        }

    }
}
