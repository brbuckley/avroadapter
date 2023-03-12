package avroadapter.api.controller;

import avroadapter.api.model.purchasems.purchase.PurchaseRequest;
import avroadapter.service.SupplierService;
import avroadapter.util.HeadersUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Controller for purchase services. */
@AllArgsConstructor
@RestController
@Validated
public class PurchaseController {

  private final SupplierService supplierService;

  /**
   * Post purchase endpoint.
   *
   * @param correlationId Correlation id header.
   * @param purchase Purchase Request.
   * @return 200.
   * @throws JsonProcessingException Json Processing Exception.
   */
  @PostMapping(value = "/", produces = "application/json")
  public ResponseEntity postPurchase(
      @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId,
      @Valid @RequestBody PurchaseRequest purchase)
      throws IOException {
    supplierService.sendToSupplier(correlationId, purchase);
    return ResponseEntity.ok().headers(HeadersUtil.defaultHeaders(correlationId)).build();
  }
}
