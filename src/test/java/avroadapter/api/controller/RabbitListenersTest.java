package avroadapter.api.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import avroadapter.util.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import avroadapter.AdapterResponseUtil;
import avroadapter.listener.RabbitListeners;
import avroadapter.service.PurchaseService;
import avroadapter.service.SupplierService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {Config.class})
public class RabbitListenersTest {

  private RabbitListeners underTest;

  private static final String LISTENER_CONTAINER_ID = "supplier";

  @Autowired private RabbitListenerTestHarness harness;
  @Autowired private TestRabbitTemplate testRabbitTemplate;

  @Mock(answer = RETURNS_DEEP_STUBS)
  private Message message;

  @MockBean private SupplierService supplierService;
  @MockBean private PurchaseService purchaseService;

  @BeforeEach
  void setUp() {
    underTest = harness.getSpy(LISTENER_CONTAINER_ID);
    assertNotNull(underTest);
  }

  @AfterEach
  void tearDown() {
    reset(underTest);
  }

  @Test
  void givenBlankApplicationEvent_handle_throwsMessageConversionExceptionToAvoidRequeue()
      throws JsonProcessingException {
    given(
            supplierService.parseOrder(
                "correlation",
                "{\"namespace\":\"com.marlo.training.api.model.supplier\",\"type\":\"record\",\"name\":\"SupplierOrder\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"purchase_id\",\"type\":\"string\"}]}",
                AdapterResponseUtil.defaultPayload()))
        .willReturn(AdapterResponseUtil.defaultSupplierResponse());

    MessageProperties mock = Mockito.mock(MessageProperties.class);
    when(message.getMessageProperties()).thenReturn(mock);
    when(mock.getHeader("X-Correlation-Id")).thenReturn("correlation");
    when(mock.getHeader("Avro-Schema"))
        .thenReturn(
            "{\"namespace\":\"com.marlo.training.api.model.supplier\",\"type\":\"record\",\"name\":\"SupplierOrder\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"purchase_id\",\"type\":\"string\"}]}");
    when(message.getBody()).thenReturn(AdapterResponseUtil.defaultPayload());

    testRabbitTemplate.convertAndSend("supplierc-adapter", "supplierc-adapter", message);

    verify(purchaseService)
        .sendToPurchase("correlation", AdapterResponseUtil.defaultSupplierResponse());
  }
}
