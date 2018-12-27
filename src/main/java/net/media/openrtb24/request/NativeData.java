package net.media.openrtb24.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeData extends AbstractExtensible<NativeData.NativeDataReqExt> {

  @NotNull
  private Integer type;

  private Integer len;

  private Map<String, Object> ext;

  public NativeData() {
    setReqExt(new NativeDataReqExt());
  }

  public static class NativeDataReqExt extends ReqExt {
  }
}
