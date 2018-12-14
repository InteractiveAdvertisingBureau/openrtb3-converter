package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

  private String id;
  private Integer test;
  private Integer tmax;
  private Integer at;
  private String[] cur;
  private String[] seat;
  private Integer wseat;
  private String cdata;
  private Source source;
  private Item[] item;
  @JsonProperty("package")
  private Integer pack;
  private Context context;
  private Ext ext;
}
