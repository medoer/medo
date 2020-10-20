package medo.common.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import medo.common.core.id.Int128;

public class Int128Module extends SimpleModule {

    private static final long serialVersionUID = -3816203229257973725L;

    class IdDeserializer extends StdScalarDeserializer<Int128> {

        private static final long serialVersionUID = 8448785930275301384L;

        public IdDeserializer() {
            super(Int128.class);
        }

        public Int128 deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.VALUE_STRING) {
                String str = jp.getText().trim();
                if (str.isEmpty()) return null;
                else return Int128.fromString(str);
            } else throw ctxt.mappingException(getValueClass());
        }
    }

    class IdSerializer extends StdScalarSerializer<Int128> {

        private static final long serialVersionUID = -7255494136593519528L;

        public IdSerializer() {
            super(Int128.class);
        }

        public void serialize(Int128 value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeString(value.asString());
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint, boolean isOptional)
                throws JsonMappingException {
            return createSchemaNode("string", true);
        }
    }

    @Override
    public String getModuleName() {
        return "IdJsonModule";
    }

    public Int128Module() {
        addDeserializer(Int128.class, new IdDeserializer());
        addSerializer(Int128.class, new IdSerializer());
    }
}
