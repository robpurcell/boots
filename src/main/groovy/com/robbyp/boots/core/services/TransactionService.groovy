/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.Transaction

interface TransactionService {

    Transaction getTransactionForId(Long transactionId)

}
