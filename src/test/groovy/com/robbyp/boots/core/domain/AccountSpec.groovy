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
import org.joda.time.Minutes
import spock.lang.Shared
import spock.lang.Specification

import static com.robbyp.boots.test.TestDataGenerators.anyString

class AccountSpec extends Specification {

    @Shared
    def today = new DateTime()
    @Shared
    def defaultGBPAccountInfo =
        new AccountInfo(uniqueId: 1L,
                        name: anyString(),
                        number: anyString(),
                        institution: anyString(),
                        currency: CurrencyUnit.GBP,
                        type: AccountType.CURRENT,
                        openingDate: today - Days.days(10),
                        openingBalance: BigMoney.parse("GBP 0")
        )
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
        account.addEntry(BigMoney.parse("GBP " + amount1), today)
        account.addEntry(BigMoney.parse("GBP " + amount2), today)

        then:
        account.balance(interval) == BigMoney.parse("GBP " + balance)

        where:
        amount1 | amount2 || balance
        100     | 30      || 130
        20      | -40     || -20
        0       | 0       || 0
    }

    def "should return the balance for entries created since account opening"() {
        when:
        def account = new Account(accountInfo: defaultGBPAccountInfo)
        account.addEntry(BigMoney.parse("GBP " + amount1), today - Days.days(5))
        account.addEntry(BigMoney.parse("GBP " + amount2), today - Days.days(2))

        then:
        account.balance(today) == BigMoney.parse("GBP " + balance)

        where:
        amount1 | amount2 || balance
        100     | 30      || 130
        20      | -40     || -20
        0       | 0       || 0
    }

    def "should return the balance for now"() {
        when:
        def account = new Account(accountInfo: defaultGBPAccountInfo)
        account.addEntry(BigMoney.parse("GBP " + amount1), today - Days.days(5))
        account.addEntry(BigMoney.parse("GBP " + amount2), today - Days.days(2))

        then:
        account.balance() == BigMoney.parse("GBP " + balance)

        where:
        amount1 | amount2 || balance
        100     | 30      || 130
        20      | -40     || -20
        0       | 0       || 0
    }

}
