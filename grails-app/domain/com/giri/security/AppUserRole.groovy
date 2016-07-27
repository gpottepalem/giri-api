package com.giri.security

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class AppUserRole implements Serializable {

	private static final long serialVersionUID = 1

	AppUser appUser
	Role role

	@Override
	boolean equals(other) {
		if (other instanceof AppUserRole) {
			other.appUserId == appUser?.id && other.roleId == role?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appUser) builder.append(appUser.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AppUserRole get(long appUserId, long roleId) {
		criteriaFor(appUserId, roleId).get()
	}

	static boolean exists(long appUserId, long roleId) {
		criteriaFor(appUserId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long appUserId, long roleId) {
		AppUserRole.where {
			appUser == AppUser.load(appUserId) &&
			role == Role.load(roleId)
		}
	}

	static AppUserRole create(AppUser appUser, Role role) {
		def instance = new AppUserRole(appUser: appUser, role: role)
		instance.save()
		instance
	}

	static boolean remove(AppUser u, Role r) {
		if (u != null && r != null) {
			AppUserRole.where { appUser == u && role == r }.deleteAll()
		}
	}

	static int removeAll(AppUser u) {
		u == null ? 0 : AppUserRole.where { appUser == u }.deleteAll()
	}

	static int removeAll(Role r) {
		r == null ? 0 : AppUserRole.where { role == r }.deleteAll()
	}

	static constraints = {
		role validator: { Role r, AppUserRole ur ->
			if (ur.appUser?.id) {
				AppUserRole.withNewSession {
					if (AppUserRole.exists(ur.appUser.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['appUser', 'role']
		version false
	}
}
