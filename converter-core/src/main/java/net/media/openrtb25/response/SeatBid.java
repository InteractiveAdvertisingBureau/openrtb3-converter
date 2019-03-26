package net.media.openrtb25.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by vishnu on 30/5/16.
 */
@Data
public class SeatBid {

  @NotNull
  @Valid
  private List<Bid> bid = new ArrayList<>();//

  private String seat;//

  private Integer group;

  private Map<String, Object> ext;//
}
