package avroadapter.mapper;

import avroadapter.api.model.purchasems.orderline.PurchaseItem;
import avroadapter.api.model.purchasems.purchase.PurchaseRequest;
import avroadapter.api.model.supplierc.orderline.SupplierItem;
import avroadapter.api.model.supplierc.purchase.SupplierRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/** Mapper for Purchase models. */
@Component
public class PurchaseMapper {

  /**
   * Parses a Supplier Response into a Purchase Response.
   *
   * @param purchase Supplier Response.
   * @return Purchase Response.
   */
  public SupplierRequest toSupplierPurchase(PurchaseRequest purchase) {
    List<SupplierItem> items = new ArrayList<>();
    for (PurchaseItem ordeLine : purchase.getItems()) {
      items.add(new SupplierItem(ordeLine.getQuantity(), ordeLine.getProduct().getId()));
    }
    return new SupplierRequest(purchase.getId(), items);
  }
}
