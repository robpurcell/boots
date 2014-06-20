/**
 * Copyright (c) 2014 Purcell Informatics Limited.
 *
 */
package com.robbyp.boots.test

import org.apache.commons.lang3.RandomStringUtils
import org.joda.time.DateTime

import java.sql.Timestamp

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

    static DateTime anyDate() {
        return new DateTime(randomTimeBetweenTwoDates())
    }

    private static final BEGIN_TIME = Timestamp.valueOf('2010-01-01 00:00:00').time
    private static final END_TIME = Timestamp.valueOf('2020-12-31 00:58:00').time

    private static long randomTimeBetweenTwoDates() {
        long diff = END_TIME - BEGIN_TIME + 1
        return BEGIN_TIME + (long) (Math.random() * diff)
    }

}
