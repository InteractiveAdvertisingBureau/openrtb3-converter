package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

import lombok.Data;

@Data
public class NativeTitle {

  private String text;

  private Map<String, Object> ext;
}
