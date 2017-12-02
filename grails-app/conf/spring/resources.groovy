import com.giri.marshallers.CustomMarshallerRegistrar
import com.giri.marshallers.DateMarshaller
import com.giri.marshallers.UUIDMarshaller
import com.giri.security.UserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener, ref('hibernateDatastore'))

    //JSON Marshallers
    customMarshallerRegistrar(CustomMarshallerRegistrar) {
        marshallers = [
            new UUIDMarshaller(),
            new DateMarshaller()
        ]
    }
}
