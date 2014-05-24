/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.persistence.integration

import static com.robbyp.boots.persistence.domain.fixture.MongoAssertions.usingMongo
import static com.robbyp.boots.persistence.domain.fixture.PersistenceFixture.standardAccount

import com.mongodb.Mongo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory

class AccountMappingIntegrationTests {

    MongoOperations mongo

    @Before
    void setup() {
        mongo = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), 'boots'))
        mongo.dropCollection('account')
    }

    @After
    void teardown() {
        mongo.dropCollection('account')
    }

    @Test
    void thatAccountIsInsertedIntoCollectionHasCorrectIndexes() {
        mongo.insert(standardAccount())

        assert 1 == mongo.getCollection('account').count()

        assert usingMongo(mongo).collection('account').hasIndexOn('_id')
//        assert usingMongo(mongo).collection('account').hasIndexOn('name')
    }

}
