package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Request {

  private String id;
  private Integer test = 0;
  private Integer tmax;
  private Integer at = 2;
  private List<String> cur = new ArrayList<String>(){{add("USD");}};
  private Set<String> seat;
  private Integer wseat = 1;
  private String cdata;
  private Source source;
  private List<Item> item;
  @JsonProperty("package")
  private Integer pack;
  private Context context;
  private Map<String, Object> ext;
}
