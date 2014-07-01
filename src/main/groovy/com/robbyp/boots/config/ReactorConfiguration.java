/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
class ReactorConfiguration {
    @Bean
    public Reactor reactor(Environment env) {
        return Reactors.reactor(env);
    }
}
