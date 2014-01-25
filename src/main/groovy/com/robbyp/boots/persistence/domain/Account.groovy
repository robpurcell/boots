/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

class Account {

    @Id
    String id

    @Indexed
    String name
    String number
    String institution
    String currency
    String type
    String openingDate
    String openingBalance

    Account(
        String name,
        String number,
        String institution,
        String currency,
        String type,
        String openingDate,
        String openingBalance)
    {
        this.name = name
        this.number = number
        this.institution = institution
        this.currency = currency
        this.type = type
        this.openingDate = openingDate
        this.openingBalance = openingBalance
    }

}
