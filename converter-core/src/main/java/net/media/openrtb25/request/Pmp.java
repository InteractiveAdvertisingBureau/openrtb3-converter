package net.media.openrtb25.request;

import java.util.List;

import lombok.Data;

/**
 * Created by vishnu on 6/5/16.
 */
@Data
public class Pmp {

  public static final Integer DEFAULT_PRIVATE_AUCTION = 0;
  private Integer private_auction = DEFAULT_PRIVATE_AUCTION;

  private List<Deal> deals;

  private Ext ext;

}
