/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */

package com.robbyp.boots.persistence.integration

import com.robbyp.boots.config.MongoConfiguration
import com.robbyp.boots.persistence.domain.fixture.PersistenceFixture
import com.robbyp.boots.persistence.repository.AccountRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = [MongoConfiguration])
class AccountRepositoryIntegrationTests {

    @Autowired
    AccountRepository accountRepository

    @Autowired
    MongoOperations mongo

    @Before
    void setup() {
        mongo.dropCollection(AccountRepository.COLLECTION_NAME)
    }

    @After
    void teardown() {
        mongo.dropCollection(AccountRepository.COLLECTION_NAME)
    }

    @Test
    void thatItemIsInsertedIntoRepoWorks() {
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 0
        accountRepository.save(PersistenceFixture.standardAccount())
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 1
    }

}

