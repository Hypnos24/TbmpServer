package com.wzq.tbmp.controller.json;

import java.io.IOException;
import java.nio.charset.Charset;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.http.converter.StringHttpMessageConverter;

public class NullSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object arg0, JsonGenerator jgen,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		jgen.writeString("");
	}

}