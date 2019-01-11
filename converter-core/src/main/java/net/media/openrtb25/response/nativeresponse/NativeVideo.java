package net.media.openrtb25.response.nativeresponse;

import lombok.Data;

import java.util.Map;

@Data
public class NativeVideo {
  private String vasttag;
  private Map<String,Object> ext;
}
