/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.CurrencyUnit
import spock.lang.Specification

import static com.robbyp.boots.test.TestDataGenerators.anyString

class AccountInfoSpec extends Specification {

    void "should create a new AccountInfo"() {
        when:
        def accountInfo =
            new AccountInfo(uniqueId: uniqueId,
                            name: name,
                            number: number,
                            institution: institution,
                            currency: CurrencyUnit.GBP,
                            type: AccountType.CURRENT)

        then:
        accountInfo.getUniqueId() == uniqueId
        accountInfo.getName() == name
        accountInfo.getNumber() == number
        accountInfo.getInstitution() == institution
        accountInfo.getCurrency() == CurrencyUnit.GBP
        accountInfo.getType() == AccountType.CURRENT

        where:
        uniqueId | name        | number      | institution
        1        | anyString() | anyString() | anyString()
        2        | anyString() | anyString() | anyString()
        3        | anyString() | anyString() | anyString()

    }
}
