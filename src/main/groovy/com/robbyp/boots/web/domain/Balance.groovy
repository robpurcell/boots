/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import org.joda.money.BigMoney

@groovy.transform.Immutable(knownImmutables = ['balance'])
class Balance {
    Long accountId
    BigMoney balance
}
