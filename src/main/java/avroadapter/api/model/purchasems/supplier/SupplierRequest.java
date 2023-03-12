package avroadapter.api.model.purchasems.supplier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Supplier Request Model. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SupplierRequest {

  @NotBlank
  @Pattern(regexp = "^SUP[0-9]{7}$")
  private String id;

  private String name;
}
