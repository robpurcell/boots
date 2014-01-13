/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import groovy.transform.Immutable
import org.joda.money.BigMoney
import org.joda.time.LocalDate

@Immutable(knownImmutables = ['price', 'date'])
class Transaction {

    Long uniqueId
    BigDecimal quantity
    BigMoney price
    LocalDate date
    String type
    String description
    Long accountId

    BigMoney getTotal() {
        return price.multipliedBy(quantity)
    }

    @Override
    String toString() {
        "Transaction: ${description}, for ${price} on ${date}, of ${type}."
    }

}

