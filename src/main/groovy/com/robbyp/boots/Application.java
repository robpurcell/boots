package com.robbyp.boots;

import com.robbyp.boots.core.domain.AccountInfo;
import com.robbyp.boots.core.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.spring.context.config.EnableReactor;

import static reactor.event.selector.Selectors.$;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableReactor
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class Application {

    @Bean
    public Reactor reactor(Environment env, AccountService accountService) {
        Logger log = LoggerFactory.getLogger("trade.server");
        Reactor r = Reactors.reactor(env);

        r.on($("account.create"), (Event<AccountInfo> ev) -> {
            accountService.add(ev.getData());
            log.info("Created account: {}", ev.getData());
        });

        r.on($("account.list"), (Event<String> ev) -> {
            accountService.list();
            log.info("Listing all accounts");
        });

        return r;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

}
