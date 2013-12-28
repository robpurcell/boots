/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.robbyp.boots.core.domain.Account
import com.robbyp.boots.web.controller.AccountController
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAssembler() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource resource = createResourceWithId(account.uniqueId, account);
        return resource;
    }

    @Override
    protected AccountResource instantiateResource(Account account) {
        return new AccountResource(
                account.uniqueId,
                account.name,
                account.number,
                account.institution,
                account.currency,
                account.type
        )
    }
}