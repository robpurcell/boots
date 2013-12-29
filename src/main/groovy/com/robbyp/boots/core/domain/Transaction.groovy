/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.time.*

class Transaction {

    BigDecimal quantity = 0
    BigDecimal price = 0
    LocalDate date
    //String investment
    String type
    String description
    //static belongsTo = [account:Account]

    BigDecimal getTotal() {
        return quantity * price
    }

    String toString() {
        "Transaction: ${description}, for ${price} on ${date}, of ${type}."
    }
}

