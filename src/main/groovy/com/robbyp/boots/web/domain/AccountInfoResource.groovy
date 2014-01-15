/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.robbyp.boots.core.domain.AccountType
import org.joda.money.CurrencyUnit
import org.springframework.hateoas.ResourceSupport

class AccountInfoResource extends ResourceSupport {

    Long uniqueId
    String name
    String number
    String institution
    String currency
    String type

    AccountInfoResource(
        Long uniqueId,
        String name,
        String number,
        String institution,
        CurrencyUnit currency,
        AccountType type)
    {
        this(uniqueId.toString(), name, number, institution, currency.code, type.toString())
    }

    @JsonCreator
    AccountInfoResource(@JsonProperty('uniqueId') String uniqueId,
                        @JsonProperty('name') String name,
                        @JsonProperty('number') String number,
                        @JsonProperty('institution') String institution,
                        @JsonProperty('currency') String currency,
                        @JsonProperty('type') String type)
    {
        this.uniqueId = uniqueId.toLong()
        this.name = name
        this.number = number
        this.institution = institution
        this.currency = currency
        this.type = type
    }
}

