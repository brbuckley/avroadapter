package avroadapter;

import avroadapter.api.model.purchasems.orderline.PurchaseItem;
import avroadapter.api.model.purchasems.product.ProductRequest;
import avroadapter.api.model.purchasems.purchase.PurchaseRequest;
import avroadapter.api.model.purchasems.purchase.PurchaseResponse;
import avroadapter.api.model.purchasems.supplier.SupplierRequest;
import avroadapter.api.model.supplierc.supplier.SupplierResponse;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdapterResponseUtil {

  public static PurchaseRequest defaultPurchaseRequest() {
    List<PurchaseItem> items = new ArrayList<>();
    items.add(defaultOrderLineRequest());
    items.add(anotherOrderLineRequest());
    return new PurchaseRequest(
        "PUR0000001",
        "ORD0000001",
        defaultSupplierRequest(),
        "pending",
        Timestamp.valueOf("2022-07-29 12:16:00"),
        items);
  }

  public static PurchaseItem defaultOrderLineRequest() {
    return new PurchaseItem(1, defaultProductRequest());
  }

  public static PurchaseItem anotherOrderLineRequest() {
    return new PurchaseItem(2, anotherProductRequest());
  }

  public static ProductRequest defaultProductRequest() {
    return new ProductRequest("PRD0000001", "Heineken", new BigDecimal("10"), "beer");
  }

  public static ProductRequest anotherProductRequest() {
    return new ProductRequest("PRD0000002", "Amstel", new BigDecimal("4.99"), "beer");
  }

  public static SupplierRequest defaultSupplierRequest() {
    return new SupplierRequest("SUP0000001", "Supplier A");
  }

  public static PurchaseResponse defaultPurchaseResponse() {
    return new PurchaseResponse("PUR0000001");
  }

  public static byte[] defaultPayload() {
    String payloadString = "\u0014SUC0000001\u0014PUR0000001";
    return payloadString.getBytes(StandardCharsets.UTF_8);
    //    GenericRecord record = new GenericData.Record(new
    // Schema.Parser().parse("{\"namespace\":\"com.marlo.training.api.model.supplier\",\"type\":\"record\",\"name\":\"SupplierOrder\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"purchase_id\",\"type\":\"string\"}]}"));
    //    record.put("id","SUP0000001");
    //    return new AvroMapper().objectToByteArray(record);
  }

  public static SupplierResponse defaultSupplierResponse() {
    return new SupplierResponse("SUC0000001", "PUR0000001");
  }
}
