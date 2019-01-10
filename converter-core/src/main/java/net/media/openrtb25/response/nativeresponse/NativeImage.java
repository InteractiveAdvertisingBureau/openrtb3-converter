package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

import lombok.Data;

@Data
public class NativeImage {
  private String url;

  private Integer w;

  private Integer h;

  private Map<String, Object> ext;
}
