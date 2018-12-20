package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Response {

  private String id;//
  private String bidid;//
  private Integer nbr;//
  private String cur;//
  private String cdata;
  private List<Seatbid> seatbid = null;//
  private Map<String,Object> ext;//

}