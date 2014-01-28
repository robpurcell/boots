/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.domain.fixture

import static com.robbyp.boots.test.TestDataGenerators.anyString

import com.robbyp.boots.persistence.domain.Account
import org.joda.time.DateTime

class PersistenceFixture {
    static Account standardAccount(String name) {
        def account = new Account(
            name,
            anyString(),
            anyString(),
            anyString(),
            anyString(),
            new DateTime().toString(),
            anyString())
        return account
    }

    static Account standardAccount() {
        def account = new Account(
            'accName',
            '12345',
            'HSBC',
            'GBP',
            'CURRENT',
            new DateTime().toString(),
            '0')
        return account
    }

}
