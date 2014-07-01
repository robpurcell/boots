/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.springframework.stereotype.Service

import com.robbyp.boots.core.domain.Account
import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.core.domain.Balance


@Service
class DefaultAccountService implements AccountService {

    private static accountInfo(Long accountId) {
        return new AccountInfo(
            accountId,
            'Current Account',
            '11-22-33 12345678',
            'HSBC',
            CurrencyUnit.GBP,
            AccountType.CURRENT,
            new DateTime(),
            BigMoney.parse('GBP 0'))
    }

    @Override
    AccountInfo getAccountInfoForId(Long accountId) {
        return accountInfo(accountId)
    }

    @Override
    Balance getAccountBalanceForId(Long accountId) {
        Account acct =
            new Account(
                uniqueId: accountId,
                accountInfo: accountInfo(accountId)
            )

        acct.addEntry(BigMoney.parse('GBP 100'), new DateTime())

        return new Balance(acct.uniqueId, acct.balance())
    }

    @Override
    AccountInfo createNewAccount(AccountInfo account) {
        return account
    }

    @Override
    void list() {

    }

}
