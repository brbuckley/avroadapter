package avroadapter.service;

import avroadapter.api.model.supplierc.supplier.SupplierResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/** Purchase services. */
@AllArgsConstructor
@Service
@Slf4j
public class PurchaseService {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper mapper;

  /**
   * Sends a message to PurchaseMS.
   *
   * @param correlationId Correlation id.
   * @param supplierOrder Supplier Order.
   * @throws JsonProcessingException Json Processing Exception.
   */
  public void sendToPurchase(String correlationId, SupplierResponse supplierOrder)
      throws JsonProcessingException {
    String json = mapper.writeValueAsString(supplierOrder);
    log.info("Sending message with id: {} & body: {}", correlationId, json);
    rabbitTemplate.convertAndSend(
        "adapter-purchase",
        json,
        m -> {
          m.getMessageProperties().getHeaders().put("X-Correlation-Id", correlationId);
          return m;
        });
  }
}
