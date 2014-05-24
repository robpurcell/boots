/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.Transaction
import com.robbyp.boots.web.domain.TransactionResource
import com.robbyp.boots.web.domain.TransactionResourceAssembler
import org.joda.money.BigMoney
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping('/transactions')
class TransactionController {

    @Autowired
    private TransactionResourceAssembler transactionResourceAssembler

    @RequestMapping(value = '/{transaction}', method = RequestMethod.GET)
    @ResponseBody
    HttpEntity<TransactionResource> show(@PathVariable Long transaction) {
        Transaction tran = new Transaction(
            transaction,
            BigDecimal.ONE,
            BigMoney.parse('USD 100'),
            new LocalDate(),
            'transactionType',
            'description',
            1L
        )

        return new ResponseEntity<TransactionResource>(
            transactionResourceAssembler.toResource(tran),
            HttpStatus.OK)
    }
}
