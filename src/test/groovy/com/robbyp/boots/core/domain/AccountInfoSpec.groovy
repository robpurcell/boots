/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import static com.robbyp.boots.test.TestDataGenerators.anyString

import org.joda.money.CurrencyUnit
import spock.lang.Specification

class AccountInfoSpec extends Specification {

    void "should create a new AccountInfo"() {
        when:
        def accountInfo =
            new AccountInfo(uniqueId: testUniqueId,
                            name: testName,
                            number: testNumber,
                            institution: testInstitution,
                            currency: CurrencyUnit.GBP,
                            type: AccountType.CURRENT)

        then:
        accountInfo.with {
            uniqueId == testUniqueId
            name == testName
            number == testNumber
            institution == testInstitution
            currency == CurrencyUnit.GBP
            type == AccountType.CURRENT
        }

        where:
        testUniqueId | testName    | testNumber  | testInstitution
        1            | anyString() | anyString() | anyString()
        2            | anyString() | anyString() | anyString()
        3            | anyString() | anyString() | anyString()

    }
}
