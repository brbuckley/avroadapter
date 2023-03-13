package avroadapter.service;

import avroadapter.api.model.purchasems.purchase.PurchaseRequest;
import avroadapter.api.model.supplierc.supplier.SupplierResponse;
import avroadapter.avromapper.AvroMapper;
import avroadapter.mapper.PurchaseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/** Services for Supplier C. */
@AllArgsConstructor
@Service
@Slf4j
public class SupplierService {

  private final ObjectMapper mapper;
  private final RabbitTemplate rabbitTemplate;
  private final PurchaseMapper purchaseMapper;
  private final AvroMapper avroMapper;

  /**
   * Parses the message payload into a Supplier Response.
   *
   * @param correlation Correlation id.
   * @param schema Avro schema as string.
   * @param message Message payload.
   * @return Supplier Response.
   */
  public SupplierResponse parseOrder(String correlation, String schema, byte[] message) {
    log.info(
        "Message received with Correlation: {} | Schema: {} | Body: {}",
        correlation,
        schema,
        message);
    try {
      GenericRecord record = (GenericRecord) avroMapper.byteArrayToObject(schema, message);
      SupplierResponse supplierResponse =
          new SupplierResponse(record.get("id").toString(), record.get("purchase_id").toString());
      log.info("Successfully parsed the message into the object: {}", supplierResponse);
      return supplierResponse;
    } catch (Exception e) {
      log.error("Poisoned message! error: {}", e.getMessage());
      throw new AmqpRejectAndDontRequeueException("Poisoned Message");
    }
  }

  /**
   * Sends an Avro message to Supplier C.
   *
   * @param correlationId Correlation id.
   * @param purchaseRequest Purchase Request.
   * @throws IOException IO Exception.
   */
  @Async
  public void sendToSupplier(String correlationId, PurchaseRequest purchaseRequest)
      throws IOException {
    System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
    String json = mapper.writeValueAsString(purchaseMapper.toSupplierPurchase(purchaseRequest));
    GenericRecord record = (GenericRecord) avroMapper.stringToObject("/avro/Purchase.avsc", json);
    byte[] data = avroMapper.objectToByteArray(record);
    log.info("Sending message with id: {} & body: {}", correlationId, data);
    Message message =
        MessageBuilder.withBody(data)
            .setHeader("X-Correlation-Id", correlationId)
            .setHeader("Avro-Schema", record.getSchema().toString())
            .build();
    rabbitTemplate.send("adapter-supplierc", message);
  }
}
