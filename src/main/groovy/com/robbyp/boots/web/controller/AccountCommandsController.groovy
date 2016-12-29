/**
 * Copyright (c) 2016 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.core.domain.AccountType
import com.robbyp.boots.core.services.AccountService
import com.robbyp.boots.web.domain.AccountInfoResource
import com.robbyp.boots.web.domain.AccountInfoResourceAssembler
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.util.UriComponentsBuilder


@Controller
@RequestMapping('/api/accounts')
class AccountCommandsController {
    @Autowired
    AccountService service

    @Autowired
    AccountInfoResourceAssembler accountInfoResourceAssembler

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    createAccount(@RequestBody AccountInfoResource accountInfoResource, UriComponentsBuilder builder) {
        AccountInfo accountInfo = service.createNewAccount(createAccountInfo(accountInfoResource))

        HttpHeaders headers = new HttpHeaders()
        headers.setLocation(
            builder
                .path('/api/accounts/{id}')
                .buildAndExpand(accountInfo.uniqueId.toString()).toUri()
        )

        return new ResponseEntity<AccountInfoResource>(
            accountInfoResourceAssembler.instantiateResource(accountInfo), headers, HttpStatus.CREATED
        )
    }

    private static createAccountInfo(AccountInfoResource accountInfoResource) {
        def currencyUnit = CurrencyUnit.getInstance(accountInfoResource.currency)
        def newAccountId = -1
        return new AccountInfo(
            newAccountId,
            accountInfoResource.name,
            accountInfoResource.number,
            accountInfoResource.institution,
            currencyUnit,
            AccountType.valueOf(accountInfoResource.type),
            DateTime.now(),
            BigMoney.zero(currencyUnit)
        )
    }
}
