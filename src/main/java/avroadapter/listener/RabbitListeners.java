package avroadapter.listener;

import avroadapter.api.model.supplierc.supplier.SupplierResponse;
import avroadapter.service.PurchaseService;
import avroadapter.service.SupplierService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** Rabbit Listeners, works like a controller. */
@AllArgsConstructor
@Component
@Slf4j
public class RabbitListeners {

  private final SupplierService supplierService;
  private final PurchaseService purchaseService;

  /**
   * Listener method, works like a controller endpoint.
   *
   * @param message Message received.
   * @throws JsonProcessingException Json Processing Exception.
   */
  @RabbitListener(id = "supplier", queues = "supplierc-adapter")
  public void listen(Message message) throws JsonProcessingException {
    String correlationId = message.getMessageProperties().getHeader("X-Correlation-Id");
    String schemaString = message.getMessageProperties().getHeader("Avro-Schema");
    SupplierResponse supplierOrder =
        supplierService.parseOrder(correlationId, schemaString, message.getBody());
    purchaseService.sendToPurchase(correlationId, supplierOrder);
  }
}
