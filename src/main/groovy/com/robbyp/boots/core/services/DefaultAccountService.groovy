package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.springframework.stereotype.Service

/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
@Service
class DefaultAccountService implements AccountService {
    @Override
    void add(AccountInfo account) {

    }

    @Override
    void list() {

    }

    @Override
    AccountInfo getAccountInfoForId(Long accountId) {
        return new AccountInfo(
            accountId,
            'Current Account',
            '11-22-33 12345678',
            'HSBC',
            CurrencyUnit.GBP,
            AccountType.CURRENT,
            new DateTime(),
            BigMoney.parse('GBP 0')
        )
    }
}
