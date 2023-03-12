package avroadapter.api.model.supplierc.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Response Model for Supplier A. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SupplierResponse {

  private String id;

  @JsonProperty(value = "purchase_id")
  private String purchaseId;
}
