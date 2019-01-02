package net.media.openrtb24.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by vishnu on 30/5/16.
 */

@Data
public class BidResponse {

  private String id;//
  private List<SeatBid> seatbid = new ArrayList<>();//
  private String bidid;//
  private String cur;//
  private String customdata;//response.ext
  private Integer nbr;//
  private Map<String, Object> ext;//

  public static final String ENCRYPT_PRICE_FLAG = "encrypt_price";

}
