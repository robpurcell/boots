/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.web.domain.BalanceResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.Environment
import reactor.core.Reactor
import reactor.core.composable.spec.Promises
import reactor.event.Event
import reactor.tuple.Tuple

@Controller
@RequestMapping('/transactions')
class TransactionController {

    @Autowired
    Environment env

    @Autowired
    private Reactor reactor

    @RequestMapping(value = '/{transaction}', method = RequestMethod.GET)
    @ResponseBody
    def asyncShow(@PathVariable Long transaction) {
        def d = Promises.<ResponseEntity<BalanceResource>> defer(env)
        reactor.notify('transaction.get', Event.wrap(Tuple.of(d, transaction)))
        return d.compose()
    }

}
