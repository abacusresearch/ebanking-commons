package ch.deeppay.util.serializer;

import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class FileFormatSerializer extends JsonSerializer<FileFormat> {
  @Override
  public void serialize(FileFormat value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.name());
  }
}
