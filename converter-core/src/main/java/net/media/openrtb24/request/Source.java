package net.media.openrtb24.request;

import lombok.Data;

import java.util.Map;

@Data
public class Source {
  private Integer fd;
  private String tid;
  private String pchain;
  private Map<String, Object> ext;
}
