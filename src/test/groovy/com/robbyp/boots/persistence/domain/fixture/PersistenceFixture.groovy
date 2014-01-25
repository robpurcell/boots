/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.domain.fixture

import com.robbyp.boots.persistence.domain.Account
import org.joda.time.DateTime

class PersistenceFixture {
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
