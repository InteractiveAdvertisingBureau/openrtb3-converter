package net.media.openrtb25.response.nativeresponse;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Link { //LinkAsset
  private String url;

  private List<String> clicktrackers;

  private String fallback;

  private Map<String, Object> ext;
}
