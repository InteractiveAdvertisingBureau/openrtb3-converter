package net.media.openrtb25.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NativeRequest {

  @JsonProperty("native")
  private NativeRequestBody nativeRequestBody;

}
