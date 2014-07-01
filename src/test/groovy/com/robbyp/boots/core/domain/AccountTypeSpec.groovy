/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import spock.lang.Specification


class AccountTypeSpec extends Specification {
    def "account type can be created from String representation"() {
        when:
        def at = AccountType.getTypeFromName(typeName)

        then:
        assert at == accountType

        where:
        typeName      || accountType
        'Current'     || AccountType.CURRENT
        'Savings'     || AccountType.SAVINGS
        'Credit Card' || AccountType.CREDITCARD
        'Mortgage'    || AccountType.MORTGAGE
    }

    def "account type can be represented as String"() {
        when:
        def friendlyName = accountType.toString()

        then:
        assert friendlyName == typeName

        where:
        accountType            || typeName
        AccountType.CURRENT    || 'Current'
        AccountType.SAVINGS    || 'Savings'
        AccountType.CREDITCARD || 'Credit Card'
        AccountType.MORTGAGE   || 'Mortgage'
    }
}
