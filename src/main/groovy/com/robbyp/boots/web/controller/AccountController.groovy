/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.Account
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.web.domain.AccountResource
import com.robbyp.boots.web.domain.AccountResourceAssembler
import org.joda.money.CurrencyUnit
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
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountResourceAssembler accountResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<AccountResource> showAll() {
        print('Hello!')
    }

    @RequestMapping(value = "/{account}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<AccountResource> show(@PathVariable Long account) {
        Account acct = new Account(
                1,
                'Current Account',
                '11-22-33 12345678',
                'HSBC',
                CurrencyUnit.GBP,
                AccountType.CURRENT)

        return new ResponseEntity<AccountResource>(
                accountResourceAssembler.toResource(acct),
                HttpStatus.OK)
    }
}