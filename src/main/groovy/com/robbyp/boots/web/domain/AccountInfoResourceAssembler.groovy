/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.robbyp.boots.core.domain.AccountInfo
import com.robbyp.boots.web.controller.AccountController
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

@Component
public class AccountInfoResourceAssembler extends ResourceAssemblerSupport<AccountInfo, AccountInfoResource> {
    public AccountInfoResourceAssembler() {
        super(AccountController.class, AccountInfoResource.class);
    }

    @Override
    public AccountInfoResource toResource(AccountInfo account) {
        AccountInfoResource resource = createResourceWithId(account.uniqueId, account);
        return resource;
    }

    @Override
    protected AccountInfoResource instantiateResource(AccountInfo account) {
        return new AccountInfoResource(
                account.uniqueId,
                account.name,
                account.number,
                account.institution,
                account.currency,
                account.type
        )
    }
}