/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.core.domain

import org.springframework.util.StringUtils

enum AccountType {

    CURRENT('cur'),
    SAVINGS('sav'),
    CREDITCARD('cre'),
    MORTGAGE('mor');

    final String id

    private AccountType(String id) {
        this.id = id
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase())
    }
}
