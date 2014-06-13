/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.services

import com.robbyp.boots.core.domain.AccountInfo

interface AccountService {
    void add(AccountInfo account)

    void list()

    AccountInfo getAccountInfoForId(Long accountId)

}
