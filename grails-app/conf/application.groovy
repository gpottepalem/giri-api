// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.giri.security.AppUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.giri.security.AppUserRole'
grails.plugin.springsecurity.authority.className = 'com.giri.security.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/',                  access: ['permitAll']],
    [pattern: '/error',             access: ['permitAll']],
    [pattern: '/index',             access: ['permitAll']],
    [pattern: '/login/auth',        access: ['denyAll']], //lock down spring security login form url
    //spring rest security api end-point
    [pattern: '/api/logout',        access: ['isAuthenticated()']],
    //Spring boot Actuator management end-points
    [pattern: '/api/management/**', access:['ROLE_ADMIN']]
]

//Spring Security REST API plugin config
String statelessFilters = 'JOINED_FILTERS, -exceptionTranslationFilter, -authenticationProcessingFilter, -securityContextPersistenceFilter, -rememberMeAuthenticationFilter'

//common
def filterChainChainMaps = [
    //Stateless chain
    [pattern: '/api/**', filters: statelessFilters],
    [pattern: '/**',     filters: statelessFilters]
    //Traditional stateful chain - We are stateless, no stateful chain is required
]

grails.plugin.springsecurity.filterChain.chainMap = filterChainChainMaps

grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'com.giri.security.AuthenticationToken'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'