package avroadapter.api.model.purchasems.orderline;

import avroadapter.api.model.purchasems.product.ProductRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** OrderLine Request Model. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PurchaseItem {

  @Positive private int quantity;
  @Valid @NotNull private ProductRequest product;
}
