package com.giri.marshallers

import javax.annotation.PostConstruct

/**
 * Custom Marshaller Registrar, registers custom object marshallers with spring.
 * Configured as a spring managed bean in resources.groovy
 *
 * @see resources.groovy
 *
 * @author Gpottepalem
 * Created on May 20, 2017
 */
class CustomMarshallerRegistrar {

    /** List of custom marshallers to be registered, initialized with bean configuration in resources.groovy */
    List<CustomObjectMarshaller> marshallers

    @PostConstruct
    void registerCustomMarshallers() {
        marshallers.each{ it.register() }
    }
}