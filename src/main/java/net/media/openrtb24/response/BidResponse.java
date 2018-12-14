package net.media.openrtb24.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.media.rtb.mowgli.annotations.ResponseSchemaInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

import static com.medianet.rtb.mowgli.utils.CommonConstants.DEFAULT_CUR;

/**
 * Created by vishnu on 30/5/16.
 */

@Data
@ResponseSchemaInfo
public class BidResponse {

  private String id;

  @JsonProperty(value = "seatbid")
  private List<SeatBid> seatBid = new ArrayList<>();
  @JsonProperty("bidid")
  private String bidId;
  private String cur = DEFAULT_CUR;
  private String customdata;
  private Integer nbr;
  private Map<String, Object> ext;

  public static final String ENCRYPT_PRICE_FLAG = "encrypt_price";

  public BidResponse() {
  }

  public SeatBid getWinnerSeat() {
    if (seatBid == null || seatBid.isEmpty()) {
      return null;
    }
    return seatBid.get(0);
  }
}
