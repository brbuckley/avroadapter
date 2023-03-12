package avroadapter.avromapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.springframework.stereotype.Component;

/** Works like an object mapper for Avro objects. */
@Slf4j
@Component
public class AvroMapper {

  /**
   * Parses a json string into an Avro Generic Record.
   *
   * @param schemaPath Path to the schema file.
   * @param jsonData Json String.
   * @return Generic Record.
   * @throws IOException IO Exception.
   */
  public Object stringToObject(String schemaPath, String jsonData) throws IOException {
    try (InputStream inputStream = getClass().getResourceAsStream(schemaPath)) {
      Schema schema = new Schema.Parser().parse(inputStream);
      DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
      return datumReader.read(null, DecoderFactory.get().jsonDecoder(schema, jsonData));
    }
  }

  /**
   * Parses a byte array to a Generic Record.
   *
   * @param schemaString Avro schema.
   * @param data byte array.
   * @return Generic Record object.
   * @throws IOException IO Exception.
   */
  public Object byteArrayToObject(String schemaString, byte[] data) throws IOException {
    Schema schema = new Schema.Parser().parse(schemaString);
    DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
    return datumReader.read(null, DecoderFactory.get().binaryDecoder(data, null));
  }

  /**
   * Parses a Generic Record into a byte array.
   *
   * @param datum Generic Record.
   * @return byte array.
   * @throws IOException IO Exception.
   */
  public byte[] objectToByteArray(GenericRecord datum) throws IOException {
    DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(datum.getSchema());
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    Encoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
    datumWriter.write(datum, encoder);
    encoder.flush();
    return stream.toByteArray();
  }
}
