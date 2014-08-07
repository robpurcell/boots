/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.robbyp.boots.core.domain.Balance
import com.robbyp.boots.web.controller.BalanceController
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

@Component
class BalanceResourceAssembler extends ResourceAssemblerSupport<Balance, BalanceResource> {
    BalanceResourceAssembler() {
        super(BalanceController, BalanceResource)
    }

    @Override
    BalanceResource toResource(Balance balance) {
        BalanceResource resource = instantiateResource(balance)
        return resource
    }

    @Override
    protected BalanceResource instantiateResource(Balance balance) {
        return new BalanceResource(
            balance.accountId,
            balance.balance
        )
    }
}
