package avroadapter.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import avroadapter.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(PurchaseController.class)
public class PurchaseControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private SupplierService supplierService;

  MediaType MEDIA_TYPE_JSON_UTF8 =
      new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

  @Test
  public void testPostPurchase_whenValid_then200() throws Exception {
    MockHttpServletRequestBuilder request = post("/");
    request.content(
        "{\"id\":\"PUR0000001\",\"order_id\":\"ORD0000001\",\"supplier\":{\"id\":\"SUP0000001\",\"name\":\"Supplier A\"},"
            + "\"items\":[{\"quantity\":1,\"product\":{\"id\":\"PRD0000001\",\"name\":\"Heineken\",\"price\":10,\"category\":\"beer\"}},"
            + "{\"quantity\":2,\"product\":{\"id\":\"PRD0000002\",\"name\":\"Amstel\",\"price\":4.99,\"category\":\"beer\"}}],"
            + "\"status\":\"processing\",\"datetime\":\"07-29-2022T12:16:00.000Z\"}");
    request.contentType(MEDIA_TYPE_JSON_UTF8);
    request.header("X-Correlation-Id", "correlation");
    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
  }
}
