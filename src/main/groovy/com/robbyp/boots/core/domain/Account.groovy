/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.time.DateTime
import org.joda.time.Interval

class Account {

    Collection<Entry> entries = new HashSet()
    AccountInfo accountInfo

    void addEntry(BigMoney amount, DateTime date) {
        BigMoney.nonNull(amount, accountInfo.getCurrency())
        entries.add(new Entry(amount, date))
    }

    BigMoney balance(Interval interval) {
        BigMoney result = BigMoney.of(accountInfo.getCurrency(), BigDecimal.ZERO)
        entries = entriesForInterval(interval)
        for (Entry entry in entries) {
            result = result.plus(entry.getAmount())
        }
        return result
    }

    List<Entry> entriesForInterval(Interval interval) {
        def resultantEntries = []
        for (Entry entry in entries) {
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

//    BigMoney deposits(Interval interval) {
//        BigMoney result = new BigMoney.of(accountInfo.getCurrency(), BigDecimal.ZERO)
//        Iterator it = entries.iterator()
//        while (it.hasNext()) {
//            Entry each = (Entry) it.next();
//            if (interval.includes(each.date()) && each.amount().isPositive())
//                result = result.add(each.amount());
//        }
//        return result;
//    }
//
//    BigMoney withdrawals(DateRange period) {
//        Money result = new Money(0, currency); Iterator it = entries.iterator();
//        while (it.hasNext()) {
//            Entry each = (Entry) it.next();
//            if (period.includes(each.date()) && each.amount().isNegative())
//                result = result.add(each.amount());
//        }
//        return result;
//    }
//

}