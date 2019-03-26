package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Source {
  private String tid;
  private Integer ts;
  private String ds;
  private String dsmap;
  private String cert;
  private String digest;
  private String pchain;
  private Map<String, Object> ext;
}
