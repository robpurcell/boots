/**
 * Copyright (c) 2013 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.web.controller

import com.robbyp.boots.web.domain.AccountInfoResource
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
@RequestMapping('/accounts')
class AccountController {

    @Autowired
    Environment env

    @Autowired
    private Reactor reactor

    @RequestMapping(value = '/{account}', method = RequestMethod.GET)
    @ResponseBody
    def asyncShow(@PathVariable Long account) {
        def d = Promises.<ResponseEntity<AccountInfoResource>> defer(env)
        reactor.notify('account.get', Event.wrap(Tuple.of(d, account)))
        return d.compose()
    }

}
