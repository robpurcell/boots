/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.time.DateTime
import org.joda.time.Interval

class Account {

    Collection<Entry> accountingEntries = new HashSet()
    AccountInfo accountInfo

    void addEntry(BigMoney amount, DateTime date) {
        BigMoney.nonNull(amount, accountInfo.getCurrency())
        accountingEntries.add(new Entry(amount, date))
    }

    void addEntry(Entry entry) {
        BigMoney.nonNull(entry.getAmount(), accountInfo.getCurrency())
        accountingEntries.add(entry)
    }

    BigMoney balance(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.getCurrency(), BigDecimal.ZERO)
        def entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            result = result.plus(entry.getAmount())
        }
        return result
    }

    List<Entry> entriesForInterval(Interval interval) {
        def resultantEntries = []
        for (Entry entry in accountingEntries) {
            if (interval.contains(entry.getDate().toInstant())) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney balance(DateTime date) {
        return balance(
            new Interval(
                accountInfo.getOpeningDate().toInstant(),
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
            if (entry.getAmount().isPositive()) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney deposits(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.getCurrency(), BigDecimal.ZERO)
        def depositEntries = depositEntriesForInterval(interval)
        for (Entry entry in depositEntries) {
            result = result.plus(entry.getAmount())
        }
        return result
    }

    List<Entry> withdrawalEntriesForInterval(Interval interval) {
        def resultantEntries = []
        def entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            if (entry.getAmount().isNegative()) {
                resultantEntries.add(entry)
            }
        }
        return resultantEntries
    }

    BigMoney withdrawals(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.getCurrency(), BigDecimal.ZERO)
        def withdrawalEntries = withdrawalEntriesForInterval(interval)
        for (Entry entry in withdrawalEntries) {
            result = result.plus(entry.getAmount())
        }
        return result
    }

}