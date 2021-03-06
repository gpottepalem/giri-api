package com.giri

import grails.rest.Resource
import grails.plugin.springsecurity.annotation.Secured

/**
 * Artist domain object.
 *
 * @author Gpottepalem
 * Created on Aug 13, 2016
 */
//@Resource(uri = '/api/artists', readOnly = false, formats = ['json', 'xml'])
//@Secured('ROLE_ADMIN')
class Artist {
    UUID id
    String firstName
    String lastName
    Date dateCreated
    Date lastUpdated

    static constraints = {
        id type: 'pg-uuid', generator: 'uuid2'
        firstName blank: false
        lastName blank: false
    }
}