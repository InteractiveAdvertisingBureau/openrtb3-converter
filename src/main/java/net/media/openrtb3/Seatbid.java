package net.media.openrtb3;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class Seatbid {

  private String seat;//
  private Integer _package;//seatbid.group
  private List<Bid> bid = null;//
  private Map<String,Object> ext;//

}