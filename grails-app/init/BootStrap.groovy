import com.giri.security.AppUser
import com.giri.security.AppUserRole
import com.giri.security.Role

class BootStrap {

    def init = {

        def adminRole = Role.findOrSaveByAuthority('ROLE_ADMIN')
        def userRole = Role.findOrSaveByAuthority('ROLE_USER')

        def testUser = AppUser.findOrSaveByUsernameAndPassword('me', 'password')

        AppUserRole.create testUser, adminRole

        AppUserRole.withSession {
            it.flush()
            it.clear()
        }

        assert AppUser.count() == 1
        assert Role.count() == 2
        assert AppUserRole.count() == 1
    }

    def destroy = {
    }
}
