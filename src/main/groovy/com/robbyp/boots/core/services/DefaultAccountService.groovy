/**
 * Copyright (c) 2016 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.Account
import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.core.domain.Balance
import com.robbyp.boots.persistence.repository.AccountRepository
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DefaultAccountService implements AccountService {

    @Autowired
    private AccountRepository repository

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
    List<AccountInfo> allAccounts() {
        return [accountInfo(1), accountInfo(2)]
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
        return repository.save(account)
    }

}
