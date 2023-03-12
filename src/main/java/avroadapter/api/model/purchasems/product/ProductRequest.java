package avroadapter.api.model.purchasems.product;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Product Request Model. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductRequest {

  @NotBlank
  @Pattern(regexp = "^PRD[0-9]{7}$")
  private String id;

  private String name;
  @NotNull @Positive private BigDecimal price;

  @Pattern(regexp = "(beer|wine)")
  private String category;
}
