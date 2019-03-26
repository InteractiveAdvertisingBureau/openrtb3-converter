package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class Deal {
  private String id;
  private Double flr;
  private String flrcur;
  private Integer at;
  private List<String> wseat;
  private List<String> wadomain;
  private Map<String, Object> ext;
}
