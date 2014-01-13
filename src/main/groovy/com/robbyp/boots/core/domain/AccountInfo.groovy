/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import groovy.transform.Immutable
import org.joda.money.CurrencyUnit

@Immutable(knownImmutables = ['currency'])
class AccountInfo {

    Long uniqueId
    String name
    String number
    String institution
    CurrencyUnit currency
    AccountType type

}