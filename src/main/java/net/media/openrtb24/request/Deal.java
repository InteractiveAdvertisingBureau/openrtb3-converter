package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by vishnu on 6/5/16.
 */
@Data
@EqualsAndHashCode(of={"id"}, callSuper=false)
public class Deal extends AbstractExtensible<Deal.DealReqExt> {
  private String id;

  @JsonProperty("bidfloor")
  private double bidFloor;

  @JsonProperty("bidfloorcur")
  private String bidFloorCur;

  private Integer at;
  private List<String> wseat;
  private List<String> wadomain;
  private Ext ext;

  public Deal() {
    setReqExt(new DealReqExt());
  }


  public String getBidFloorCur() {
    return bidFloorCur;
  }

  //Couldn't find a reliable default setting annotation
  public void setBidFloorCur(String bidFloorCur) {
    this.bidFloorCur = StringUtils.isBlank(bidFloorCur) ? "USD" : bidFloorCur;
  }

  public class Ext {

  }

  public static class DealReqExt extends ReqExt {
  }
}
