package net.media.openrtb24.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by vishnu on 30/5/16.
 */
@Data
public class SeatBid {

  private List<Bid> bid = new ArrayList<>();

  private String seat;

  private Integer group;

  private Map<String, Object> ext;

  public Bid getWinnerBid() {
    if (bid == null || bid.isEmpty()) {
      return null;
    }
    return bid.get(0);
  }
}
