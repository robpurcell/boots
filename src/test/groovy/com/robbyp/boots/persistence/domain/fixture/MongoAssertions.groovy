/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 * Original from: http://spring.io/guides/tutorials/data/2/
 *
 */
package com.robbyp.boots.persistence.domain.fixture

import com.mongodb.DBObject
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.index.IndexInfo

/**
 * Simple implementation of a fluent interface builder around MongoTemplate and Mongo.
 * Providing some assertions on collections, indexes and document structure.
 */
class MongoAssertions {

    private MongoOperations ops

    static MongoAssertions usingMongo(MongoOperations ops) {
        MongoAssertions assertions = new MongoAssertions()
        assertions.ops = ops
        return assertions
    }

    CollectionAssertions collection(String name) {
        new CollectionAssertions(name)
    }

    class CollectionAssertions {
        private final String collection

        CollectionAssertions(String name) {
            this.collection = name
        }

        boolean hasIndexOn(String... fields) {
            List<IndexInfo> indexes = ops.indexOps(collection).getIndexInfo()
            for (IndexInfo info : indexes) {
                if (info.isIndexForFields(Arrays.asList(fields))) {
                    return true
                }
            }
            return false
        }

        DocumentAssertions first() {
            return new DocumentAssertions(ops.getCollection(collection).findOne())
        }

        class DocumentAssertions {
            DBObject document

            DocumentAssertions(DBObject document) {
                this.document = document
            }

            boolean hasField(String name) {
                return document.containsField(name)
            }

            Object fieldContent(String name) {
                return document.get(name)
            }
        }
    }
}
