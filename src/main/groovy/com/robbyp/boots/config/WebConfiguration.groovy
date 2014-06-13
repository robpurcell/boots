/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import reactor.spring.webmvc.PromiseHandlerMethodReturnValueHandler

import javax.annotation.PostConstruct
import javax.annotation.Resource

@Configuration
class WebConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter

    @PostConstruct
    init() {
        def originalHandlers =
            new ArrayList<>(requestMappingHandlerAdapter.returnValueHandlers)
        originalHandlers.add(0, promiseHandlerMethodReturnValueHandler())
        requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers)
    }

    @Bean
    private static promiseHandlerMethodReturnValueHandler() {
        return new PromiseHandlerMethodReturnValueHandler()
    }

}
