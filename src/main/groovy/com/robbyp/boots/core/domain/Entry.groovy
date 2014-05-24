/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import groovy.transform.Immutable
import org.joda.money.BigMoney
import org.joda.time.DateTime

@Immutable(knownImmutables = ['amount', 'date'])
class Entry {

    BigMoney amount
    DateTime date
    //EntryType type TODO Unclear how this is used yet

}
