/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import groovy.transform.Immutable
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime

@Immutable(knownImmutables = ['currency', 'openingDate', 'openingBalance'])
class AccountInfo {

    Long uniqueId
    String name
    String number
    String institution
    CurrencyUnit currency
    AccountType type
    DateTime openingDate
    BigMoney openingBalance

}