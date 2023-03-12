package avroadapter.api.model.supplierc.purchase;

import avroadapter.api.model.supplierc.orderline.SupplierItem;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Purchase model for Supplier C. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SupplierRequest {

  private String id;
  private List<SupplierItem> items;
}
