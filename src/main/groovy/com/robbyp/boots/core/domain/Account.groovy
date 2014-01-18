/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.time.DateTime
import org.joda.time.Interval

class Account {

    Long uniqueId
    Collection<Entry> accountingEntries = [] as Set
    AccountInfo accountInfo

    void addEntry(BigMoney amount, DateTime date) {
        BigMoney.nonNull(amount, accountInfo.currency)
        accountingEntries.add(new Entry(amount, date))
    }

    void addEntry(Entry entry) {
        BigMoney.nonNull(entry.amount, accountInfo.currency)
        accountingEntries.add(entry)
    }

    BigMoney balance(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.currency, BigDecimal.ZERO)
        def entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            result = result + entry.amount
        }
        return result
    }

    List<Entry> entriesForInterval(Interval interval) {
        def resultantEntries = []
        for (Entry entry in accountingEntries) {
            if (interval.contains(entry.date.toInstant())) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney balance(DateTime date) {
        return balance(
            new Interval(
                accountInfo.openingDate.toInstant(),
                date.toInstant()
            )
        )
    }

    BigMoney balance() {
        return balance(new DateTime())
    }

    List<Entry> depositEntriesForInterval(Interval interval) {
        def resultantEntries = []
        def entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            if (entry.amount.isPositive()) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney deposits(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.currency, BigDecimal.ZERO)
        def depositEntries = depositEntriesForInterval(interval)
        for (Entry entry in depositEntries) {
            result = result + entry.amount
        }
        return result
    }

    List<Entry> withdrawalEntriesForInterval(Interval interval) {
        def resultantEntries = []
        def entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            if (entry.amount.isNegative()) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney withdrawals(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.currency, BigDecimal.ZERO)
        def withdrawalEntries = withdrawalEntriesForInterval(interval)
        for (Entry entry in withdrawalEntries) {
            result = result + entry.amount
        }
        return result
    }

}