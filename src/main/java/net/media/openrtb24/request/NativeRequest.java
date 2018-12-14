package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.media.rtb.mowgli.annotations.NativeRequestSchemaInfo;

import lombok.Data;

@Data
public class NativeRequest extends AbstractExtensible<NativeRequest.NativeRequestReqExt> {

  @JsonProperty("native")
  private NativeRequestBody nativeRequestBody;

  public NativeRequest() {
    setReqExt(new NativeRequestReqExt());
  }

  public static class NativeRequestReqExt extends ReqExt {
  }
}
