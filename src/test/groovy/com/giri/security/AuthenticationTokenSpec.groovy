package com.giri.security

import grails.testing.gorm.DomainUnitTest
import groovy.transform.NotYetImplemented
import spock.lang.Specification

/**
 * Grails 3.3.2 new trait based test framework replaced the existing @TestMixin based framework with a simpler
 * implementation
 */
class AuthenticationTokenSpec extends Specification implements DomainUnitTest<AuthenticationToken>{

    def setup() {
    }

    def cleanup() {
    }

    @NotYetImplemented
    void "test something"() {
        expect:"fix me"
            true == false
    }
}