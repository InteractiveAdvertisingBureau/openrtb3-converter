package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Regs {

  private Integer coppa;
  private Integer gdpr;
  private Map<String, Object> ext;

}
