/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.integration

import static com.robbyp.boots.test.TestDataGenerators.anyString

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
    private AccountRepository accountRepository

    @Autowired
    private MongoOperations mongo

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

    @Test
    void thatSeveralItemsAreInsertedIntoRepoWorks() {
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 0
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount())
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 3
    }

    @Test
    void thatSeveralItemsCanBeRetrieved() {
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 0
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount())
        def result = accountRepository.findAll()
        assert result.size() == 3
    }

    @Test
    void thatAnItemCanBeRetrievedByName() {
        // Given
        def testName = anyString()

        // When
        assert mongo.getCollection(AccountRepository.COLLECTION_NAME).count() == 0
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount())
        accountRepository.save(PersistenceFixture.standardAccount(testName))

        // Then
        def result = accountRepository.findByNameIn(testName)
        assert result.size() == 1
        assert result[0].name == testName
    }

}

