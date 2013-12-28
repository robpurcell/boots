/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.CurrencyUnit

class Account {

    Long uniqueId
    String name
    String number
    String institution
    CurrencyUnit currency
    AccountType type

    Account(Long uniqueId, String name, String number, String institution, CurrencyUnit currency, AccountType type) {
        this.uniqueId = uniqueId
        this.name = name
        this.number = number
        this.institution = institution
        this.currency = currency
        this.type = type
    }

}