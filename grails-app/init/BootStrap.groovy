import com.giri.security.AppUser
import com.giri.security.AppUserRole
import com.giri.security.Role

class BootStrap {

    def init = {

        def adminRole = Role.findOrSaveByAuthority('ROLE_ADMIN')
        def userRole = Role.findOrSaveByAuthority('ROLE_USER')

        def adminUser = AppUser.findOrSaveByUsernameAndPassword('admin', 'admin')
        def testUser = AppUser.findOrSaveByUsernameAndPassword('me', 'password')

        AppUserRole.create testUser, userRole
        AppUserRole.create adminUser, adminRole

        AppUserRole.withSession {
            it.flush()
            it.clear()
        }

        assert AppUser.count() == 2
        assert Role.count() == 2
        assert AppUserRole.count() == 2
    }

    def destroy = {
    }
}