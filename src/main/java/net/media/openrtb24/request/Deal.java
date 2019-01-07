package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by vishnu on 6/5/16.
 */
@Data
public class Deal {
  private String id;

  @JsonProperty("bidfloor")
  private double bidFloor;

  @JsonProperty("bidfloorcur")
  private String bidFloorCur;

  private Integer at;
  private List<String> wseat;
  private List<String> wadomain;
  private Map<String, Object> ext;


  public String getBidFloorCur() {
    return bidFloorCur;
  }

  //Couldn't find a reliable default setting annotation
  public void setBidFloorCur(String bidFloorCur) {
    this.bidFloorCur = StringUtils.isBlank(bidFloorCur) ? "USD" : bidFloorCur;
  }
}
