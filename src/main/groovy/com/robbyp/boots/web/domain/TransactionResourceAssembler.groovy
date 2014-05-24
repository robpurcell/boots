/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.robbyp.boots.core.domain.Transaction
import com.robbyp.boots.web.controller.TransactionController
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

@Component
class TransactionResourceAssembler
    extends ResourceAssemblerSupport<Transaction, TransactionResource> {

    TransactionResourceAssembler() {
        super(TransactionController, TransactionResource)
    }

    @Override
    TransactionResource toResource(Transaction transaction) {
        TransactionResource resource = createResourceWithId(transaction.uniqueId, transaction)
        return resource
    }

    @Override
    protected TransactionResource instantiateResource(Transaction transaction) {
        return new TransactionResource(
            transaction.uniqueId,
            transaction.quantity,
            transaction.price,
            transaction.date,
            transaction.type,
            transaction.description,
            transaction.accountId
        )
    }
}
