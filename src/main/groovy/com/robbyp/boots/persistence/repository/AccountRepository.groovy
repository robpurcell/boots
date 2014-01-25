/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.repository

import com.robbyp.boots.persistence.domain.Account
import org.springframework.data.mongodb.repository.MongoRepository


interface AccountRepository extends MongoRepository<Account, String> {
    static final COLLECTION_NAME = 'account'

    Account findById(String id)

}
