/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.money.BigMoney
import org.joda.time.LocalDate
import org.springframework.hateoas.ResourceSupport

@JsonIgnoreProperties(['_links'])
class TransactionResource extends ResourceSupport {

    Long uniqueId
    String quantity
    String price
    String date
    String type
    String description
    Long accountId

    TransactionResource(
        Long uniqueId,
        BigDecimal quantity,
        BigMoney price,
        LocalDate date,
        String type,
        String description,
        Long accountId)
    {
        this(uniqueId.toString(),
             quantity.toString(),
             price.toString(),
             date.toString(),
             type,
             description,
             accountId.toString()
        )
    }

    @JsonCreator
    TransactionResource(
        @JsonProperty('uniqueId') String uniqueId,
        @JsonProperty('quantity') String quantity,
        @JsonProperty('price') String price,
        @JsonProperty('date') String date,
        @JsonProperty('type') String type,
        @JsonProperty('description') String description,
        @JsonProperty('accountId') String accountId)
    {
        this.uniqueId = uniqueId.toLong()
        this.quantity = quantity
        this.price = price
        this.date = date
        this.type = type
        this.description = description
        this.accountId = accountId.toLong()
    }

}
