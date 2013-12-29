/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.LocalDate
import spock.lang.Specification

class TransactionSpec extends Specification {

    void "should return a total value"() {
        when:
        def transaction = new Transaction(
                new BigDecimal(quantity),
                BigMoney.parse(price),
                date,
                type,
                description,
                account)

        then:
        transaction.getTotal() == BigMoney.parse(total)

        where:
        quantity | price     || total
        1        | "GBP 10"  || "GBP 10"
        2        | "GBP 10"  || "GBP 20"
        5        | "GBP -10" || "GBP -50"

        date = new LocalDate()
        type = 'anyString'
        description = 'anyString'
        account = new Account(1L, 'anyString', 'anyString', 'anyString', CurrencyUnit.GBP, AccountType.CURRENT)
    }
}
