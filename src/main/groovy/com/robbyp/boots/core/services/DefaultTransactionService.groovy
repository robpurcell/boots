/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.Transaction
import org.joda.money.BigMoney
import org.joda.time.LocalDate
import org.springframework.stereotype.Service

@Service
class DefaultTransactionService implements TransactionService {

    @Override
    Transaction getTransactionForId(Long transactionId) {
        return new Transaction(
            transactionId,
            BigDecimal.ONE,
            BigMoney.parse('USD 100'),
            new LocalDate(),
            'transactionType',
            'description',
            1L
        )
    }

}
