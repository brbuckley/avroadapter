package avroadapter.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import avroadapter.AdapterResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PurchaseServiceTest {

  @Test
  public void testSendToPurchase_whenValid_thenDontThrow() {
    // Mock
    RabbitTemplate mockRabbit = Mockito.spy(RabbitTemplate.class);
    doNothing().when(mockRabbit).send(any(), anyString(), any(), any());

    PurchaseService service = new PurchaseService(mockRabbit, new ObjectMapper());

    assertDoesNotThrow(
        () -> service.sendToPurchase("correlation", AdapterResponseUtil.defaultSupplierResponse()));
  }
}
