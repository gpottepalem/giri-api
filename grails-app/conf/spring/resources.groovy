import com.giri.marshallers.CustomMarshallerRegistrar
import com.giri.marshallers.DateMarshaller
import com.giri.marshallers.UUIDMarshaller

// Place your Spring DSL code here
beans = {
    //JSON Marshallers
    customMarshallerRegistrar(CustomMarshallerRegistrar) {
        marshallers = [
            new UUIDMarshaller(),
            new DateMarshaller()
        ]
    }
}
