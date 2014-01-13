/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.money.CurrencyMismatchException
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Interval
import org.joda.time.LocalDate
import spock.lang.Shared
import spock.lang.Specification

import static com.robbyp.boots.test.TestDataGenerators.anyString

class AccountSpec extends Specification {

    @Shared
    def defaultGBPAccountInfo =
        new AccountInfo(uniqueId: 1L,
                        name: anyString(),
                        number: anyString(),
                        institution: anyString(),
                        currency: CurrencyUnit.GBP,
                        type: AccountType.CURRENT)
    @Shared
    def today = new DateTime()
    @Shared
    def intervalStart = (today - Days.ONE).toInstant()
    @Shared
    def intervalStop = (today + Days.ONE).toInstant()
    @Shared
    def interval = new Interval(intervalStart, intervalStop)

    def "duplicate entries cannot be added to the account"() {
        setup:
        def account = new Account(accountInfo: defaultGBPAccountInfo)

        when:
        account.addEntry(BigMoney.parse("GBP 10"), today)
        account.addEntry(BigMoney.parse("GBP 10"), today)

        then:
        account.entries.size() == 1
    }

    def "should throw an exception if entry does not have account currency"() {
        setup:
        def account = new Account(accountInfo: defaultGBPAccountInfo)

        when:
        account.addEntry(BigMoney.parse("USD 10"), today)

        then:
        thrown(CurrencyMismatchException)
    }

    def "should check the currency of money matches the account"() {
        setup:
        def account = new Account(accountInfo: defaultGBPAccountInfo)

        when:
        account.addEntry(BigMoney.parse("GBP 10"), today)

        then:
        notThrown(CurrencyMismatchException)
    }

    def "should add entries to the account"() {
        setup:
        def account = new Account(accountInfo: defaultGBPAccountInfo)

        when:
        account.addEntry(BigMoney.parse("GBP 10"), today)

        then:
        account.getEntries().toList() == [new Entry(BigMoney.parse("GBP 10"), today)]

    }

    def "should return a list of entries in the date interval"() {
        given:
        def account = new Account(accountInfo: defaultGBPAccountInfo)
        account.addEntry(BigMoney.parse("GBP 10"), first)
        account.addEntry(BigMoney.parse("GBP 20"), middle)
        account.addEntry(BigMoney.parse("GBP 30"), last)

        when:
        def entries = account.entriesForInterval(interval)

        then:
        entries.size() == numberOfEntries

        where:
        first            | middle           | last             || numberOfEntries
        today - Days.TWO | today - Days.TWO | today - Days.TWO || 0
        today            | today            | today            || 3
        today - Days.ONE | today            | today + Days.ONE || 2
        today            | today + Days.ONE | today + Days.TWO || 1
        today + Days.TWO | today + Days.TWO | today + Days.TWO || 0
    }

    def "should return the total of the entries"() {
        when:
        def account = new Account(accountInfo: defaultGBPAccountInfo)
        def today = new DateTime()
        account.addEntry(BigMoney.parse("GBP " + amount1), today)
        account.addEntry(BigMoney.parse("GBP " + amount2), today)

        then:
        account.balance(interval) == BigMoney.parse(balance)

        where:
        amount1 | amount2 || balance
        100     | 30      || "GBP 130"
        20      | -40     || "GBP -20"
        0       | 0       || "GBP 0"
    }

}
