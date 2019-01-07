package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Dooh extends DistributionChannel {

  private Integer venue;
  private Integer fixed;
  private Integer etime;
  private Integer dpi;
  private Map<String, Object> ext;
}
