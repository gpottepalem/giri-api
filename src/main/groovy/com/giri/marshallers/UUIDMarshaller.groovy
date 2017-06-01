package com.giri.marshallers

import grails.converters.JSON

/**
 * UUID marshaller, registers a {@link JSON} marshaller to output the string representation of {@link UUID}
 *
 * @author Gpottepalem
 * Created on May 20, 2017
 */
class UUIDMarshaller implements CustomObjectMarshaller {

    @Override
    void register(){
        JSON.registerObjectMarshaller(UUID){ UUID uuid->
            return uuid.toString()
        }
    }
}