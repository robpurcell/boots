/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.web.domain.AccountInfoResource
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler
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
@RequestMapping('/accounts')
class AccountController {

    @Autowired
    private AccountInfoResourceAssembler accountInfoResourceAssembler

    @RequestMapping(value = '/{account}', method = RequestMethod.GET)
    @ResponseBody
    HttpEntity<AccountInfoResource> show(@PathVariable Long account) {
        AccountInfo acct = new AccountInfo(
            account,
            'Current Account',
            '11-22-33 12345678',
            'HSBC',
            CurrencyUnit.GBP,
            AccountType.CURRENT,
            new DateTime(),
            BigMoney.parse('GBP 0'))

        return new ResponseEntity<AccountInfoResource>(
            accountInfoResourceAssembler.toResource(acct),
            HttpStatus.OK)
    }

}
