package avroadapter.api.model.purchasems.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

public class SupplierRequestTest {

  @Test
  public void testDateTime_whenNull_thenGet() {
    PurchaseRequest request = new PurchaseRequest(null, null, null, null, null, null);
    request.setDatetime(Timestamp.valueOf("2022-07-29 12:16:00"));
    request.setDatetime(null);
    assertNull(request.getDatetime());
  }

  @Test
  public void testDateTime_whenValid_thenGet() {
    PurchaseRequest request = new PurchaseRequest(null, null, null, null, null, null);
    request.setDatetime(Timestamp.valueOf("2022-07-29 12:16:00"));
    assertEquals("2022-07-29 12:16:00.0", request.getDatetime().toString());
  }
}
