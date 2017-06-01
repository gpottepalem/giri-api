package com.giri.marshallers

import grails.converters.JSON

/**
 * Date marshaller, registers a {@link JSON} marshaller to output the string representation of {@link Date}
 * @author Gpottepalem
 * Created on May 20, 2017
 */
class DateMarshaller implements CustomObjectMarshaller {
    @Override
    void register() {
        JSON.registerObjectMarshaller(Date) {Date date ->
            return date.format('MM/dd/yyyy')
        }
    }
}