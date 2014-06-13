/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.config;

import static reactor.event.selector.Selectors.$;

import com.robbyp.boots.core.domain.AccountInfo;
import com.robbyp.boots.core.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
class ReactorConfiguration {
    @Bean
    public Reactor reactor(Environment env, AccountService accountService) {
        Logger log = LoggerFactory.getLogger("boots");
        Reactor r = Reactors.reactor(env);

        r.on($("account.create"), (Event< AccountInfo > ev) -> {
            accountService.add(ev.getData());
            log.info("Created account: {}", ev.getData());
        } );

        r.on($("account.list"), (Event < String > ev) -> {
            accountService.list();
            log.info("Listing all accounts");
        } );

        return r;
    }
}
