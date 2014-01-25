/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.config

import com.mongodb.Mongo
import com.robbyp.boots.persistence.repository.AccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(
    basePackages = 'com.robbyp.boots.persistence.repository',
    includeFilters = @ComponentScan.Filter(
        value = [AccountRepository],
        type = FilterType.ASSIGNABLE_TYPE)
)
class MongoConfiguration {

    @Bean
    MongoTemplate mongoTemplate(Mongo mongo) throws UnknownHostException {
        return new MongoTemplate(mongo, 'boots')
    }

    @Bean
    Mongo mongo() throws UnknownHostException {
        return new Mongo('localhost')
    }
}
