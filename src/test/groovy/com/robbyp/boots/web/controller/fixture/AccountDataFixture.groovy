/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller.fixture

import static com.robbyp.boots.test.TestDataGenerators.anyAccountType
import static com.robbyp.boots.test.TestDataGenerators.anyCurrency
import static com.robbyp.boots.test.TestDataGenerators.anyInt
import static com.robbyp.boots.test.TestDataGenerators.anyLong
import static com.robbyp.boots.test.TestDataGenerators.anyString

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.web.domain.AccountInfoResource


class AccountDataFixture {

    static final AccountInfo standardAccountInfo() {
        return new AccountInfo(
            uniqueId: -1,
            name: anyString(),
            number: anyString(),
            institution: anyString(),
            currency: anyCurrency(),
            type: anyAccountType()
        )

    }

    static final AccountInfoResource standardAccountInfoResource() {
        return new AccountInfoResource(
            -1,
            anyString(),
            anyString(),
            anyString(),
            anyCurrency(),
            anyAccountType()
        )
    }

    static final AccountInfoResource standardAccountInfoResource(AccountInfo accountInfo) {
        new AccountInfoResource(
            accountInfo.uniqueId,
            accountInfo.name,
            accountInfo.number,
            accountInfo.institution,
            accountInfo.currency,
            accountInfo.type
        )
    }

    static final String standardAccountInfoJSON() {
        def accountInfo = standardAccountInfo()
        return """
            {
                "name":" ${accountInfo.name}",
                "number": "${accountInfo.number}",
                "institution": "${accountInfo.institution}",
                "currency": "${accountInfo.currency.code}",
                "type": "${accountInfo.type.name()}"
            }
        """
    }

    static final String standardAccountInfoJSON(AccountInfo accountInfo) {
        return """
            {
                "name":"${accountInfo.name}",
                "number":"${accountInfo.number}",
                "institution":"${accountInfo.institution}",
                "currency":"${accountInfo.currency.code}",
                "type":"${accountInfo.type.name()}"
            }
        """
    }

}
