/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.Balance

interface AccountService {

    AccountInfo getAccountInfoForId(Long accountId)

    Balance getAccountBalanceForId(Long accountId)

    AccountInfo createNewAccount(AccountInfo account)

    void list()

}
