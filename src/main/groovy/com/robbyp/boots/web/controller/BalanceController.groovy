/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.Account
import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.web.domain.Balance
import com.robbyp.boots.web.domain.BalanceResource
import com.robbyp.boots.web.domain.BalanceResourceAssembler
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping('/balances')
class BalanceController {

    @Autowired
    private BalanceResourceAssembler balanceResourceAssembler

    @RequestMapping(value = '/{account}', method = RequestMethod.GET)
    @ResponseBody
    HttpEntity<BalanceResource> show(@PathVariable Long account) {
        AccountInfo accountInfo = new AccountInfo(
            account,
            'Current Account',
            '11-22-33 12345678',
            'HSBC',
            CurrencyUnit.GBP,
            AccountType.CURRENT,
            new DateTime(),
            BigMoney.parse('GBP 0'))

        Account acct =
            new Account(
                uniqueId: account,
                accountInfo: accountInfo
            )

        acct.addEntry(BigMoney.parse('GBP 100'), new DateTime())

        Balance balance = new Balance(acct.uniqueId, acct.balance())

        return new ResponseEntity<BalanceResource>(
            balanceResourceAssembler.toResource(balance),
            HttpStatus.OK)
    }

}
