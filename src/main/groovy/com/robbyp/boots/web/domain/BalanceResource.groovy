/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.money.BigMoney
import org.springframework.hateoas.ResourceSupport

@JsonIgnoreProperties(['_links'])
class BalanceResource extends ResourceSupport {

    Long accountId
    String currency
    String balance

    BalanceResource(Long accountId, BigMoney balance) {
        this(accountId.toString(),
             balance.currencyUnit.code,
             balance.amount.toString()
        )
    }

    @JsonCreator
    BalanceResource(@JsonProperty('accountId') String accountId,
                    @JsonProperty('currency') String currency,
                    @JsonProperty('balance') String balance)
    {
        this.accountId = accountId.toLong()
        this.currency = currency
        this.balance = balance
    }
}

