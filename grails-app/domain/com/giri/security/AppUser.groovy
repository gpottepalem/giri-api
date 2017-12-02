package com.giri.security

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class AppUser implements Serializable {

	private static final long serialVersionUID = 1

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<Role> getAuthorities() {
		(AppUserRole.findAllByAppUser(this) as List<AppUserRole>)*.role as Set<Role>
	}

	static transients = ['springSecurityService']

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
	}

	static mapping = {
		password column: '`password`'
	}
}
