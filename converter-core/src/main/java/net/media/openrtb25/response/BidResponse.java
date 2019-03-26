package net.media.openrtb25.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by vishnu on 30/5/16.
 */
@Data
public class BidResponse {

  @NotNull
  private String id;//
  @Valid
  private List<SeatBid> seatbid = new ArrayList<>();//
  private String bidid;//
  private String cur;//
  private String customdata;//response.ext
  private Integer nbr;//
  private Map<String, Object> ext;//

  public static final String ENCRYPT_PRICE_FLAG = "encrypt_price";

}
