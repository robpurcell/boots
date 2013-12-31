/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.joda.money.BigMoney
import org.joda.time.LocalDate

class Transaction {

    private Long uniqueId
    private BigDecimal quantity
    private BigMoney price
    private LocalDate date
    private String type
    private String description
    private Long accountId

    Transaction(
            Long uniqueId,
            BigDecimal quantity,
            BigMoney price,
            LocalDate date,
            String type,
            String description,
            Long accountId)
    {
        this.uniqueId = uniqueId
        this.quantity = quantity
        this.price = price
        this.date = date
        this.type = type
        this.description = description
        this.accountId = accountId
    }

    Long getUniqueId() {
        return uniqueId
    }

    BigDecimal getQuantity() {
        return quantity
    }

    BigMoney getPrice() {
        return price
    }

    LocalDate getDate() {
        return date
    }

    String getType() {
        return type
    }

    String getDescription() {
        return description
    }

    Long getAccountId() {
        return accountId
    }

    BigMoney getTotal() {
        return price.multipliedBy(quantity)
    }

    @Override
    String toString() {
        "Transaction: ${description}, for ${price} on ${date}, of ${type}."
    }

}

