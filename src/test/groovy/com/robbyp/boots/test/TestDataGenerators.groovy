/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.test

import org.apache.commons.lang3.RandomStringUtils

class TestDataGenerators {

    static String anyString() {
        anyString(Math.floor(Math.random() * 10).intValue())
    }

    static String anyString(Integer length) {
        return RandomStringUtils.random(length)
    }

    static Character anyChar() {
        return RandomStringUtils.random(1)
    }
}
