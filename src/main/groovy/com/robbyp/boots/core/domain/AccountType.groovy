/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.apache.commons.lang3.StringUtils


enum AccountType {

    CURRENT('Current'),
    SAVINGS('Savings'),
    CREDITCARD('Credit Card'),
    MORTGAGE('Mortgage')

    final String typeName

    private AccountType(String typeName) {
        this.typeName = typeName
    }

    static AccountType getTypeFromName(String name) {
        return StringUtils.deleteWhitespace(name).toUpperCase() as AccountType
    }

    @Override
    String toString() {
        return this.typeName
    }
}
