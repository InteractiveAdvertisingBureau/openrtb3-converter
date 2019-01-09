package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

import lombok.Data;

@Data
public class NativeData {
  private String label;

  private String value;

  private Map<String, Object> ext;
}
