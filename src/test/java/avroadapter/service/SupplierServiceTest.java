package avroadapter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import avroadapter.AdapterResponseUtil;
import avroadapter.avromapper.AvroMapper;
import avroadapter.mapper.PurchaseMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class SupplierServiceTest {

  SupplierService service;

  @Test
  public void testParseOrder_whenValid_thenReturnResponse() {
    service = new SupplierService(new ObjectMapper(), null, null, new AvroMapper());

    assertEquals(
        "SUC0000001",
        service
            .parseOrder(
                "correlation",
                "{\"namespace\":\"com.marlo.training.api.model.supplier\",\"type\":\"record\",\"name\":\"SupplierOrder\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"purchase_id\",\"type\":\"string\"}]}",
                AdapterResponseUtil.defaultPayload())
            .getId());
  }

  @Test
  public void testParseOrder_whenInvalid_thenThrow() {
    service = new SupplierService(new ObjectMapper(), null, null, new AvroMapper());
    byte[] payload = null;

    assertThrows(
        AmqpRejectAndDontRequeueException.class,
        () ->
            service.parseOrder(
                "correlation",
                "{\"namespace\":\"com.marlo.training.api.model.supplier\",\"type\":\"record\",\"name\":\"SupplierOrder\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"purchase_id\",\"type\":\"string\"}]}",
                payload));
  }

  @Test
  public void testSendToSupplier_whenValid_thenReturnResponse() throws IOException {
    // Mock
    RabbitTemplate mockRabbit = Mockito.spy(RabbitTemplate.class);
    doNothing().when(mockRabbit).send(any(), anyString(), any(), any());

    service =
        new SupplierService(new ObjectMapper(), mockRabbit, new PurchaseMapper(), new AvroMapper());
    service.sendToSupplier("correlation", AdapterResponseUtil.defaultPurchaseRequest());

    verify(mockRabbit).send(any(), anyString(), any(), any());
  }
}
