package com.giri

import com.giri.security.AppUser
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.transaction.Transactional
import org.springframework.http.HttpStatus

/**
 * Customized Artists RestfulController.
 *
 * @author Gpottepalem
 * Created on May 26, 2017
 */
class ArtistController extends RestfulController {

    static responseFormats = ['json', 'xml']

    SpringSecurityService springSecurityService

    ArtistController() {
        super(Artist)
    }

    //@Secured(value = ['permitAll'], httpMethod = 'GET')
    //@Secured(value = ['permitAll'])
    //@Secured('ROLE_USER')
    @Secured('permitAll')
    @Override
    def index(Integer max) {
        respond([message: 'Access Denied'], status: HttpStatus.FORBIDDEN)
        return
        //super.index(max)
    }

    @Secured('ROLE_USER')
    @Override
    def show() {
        println "id:${params.id}"
        super.show()
    }

    @Secured('ROLE_ADMIN')
    @Override
    //@Transactional - exception
    def save() {
        super.save()
    }

    @Secured('ROLE_USER')
    @Override
//    @Transactional
    def update() {
        //AppUser currentUser = springSecurityService.currentUser as AppUser
        super.update()
    }

    @Secured('ROLE_ADMIN')
    @Override
//    @Transactional
    def delete() {
        super.delete()
    }
}