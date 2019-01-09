package net.media.openrtb24.response.nativeresponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NativeResponse {

  @JsonProperty("native")
  private NativeResponseBody nativeResponseBody;

}
