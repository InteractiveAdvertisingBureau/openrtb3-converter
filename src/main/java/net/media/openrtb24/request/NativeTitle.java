package net.media.openrtb24.request;

import java.util.Map;

import lombok.Data;

@Data
public class NativeTitle extends AbstractExtensible<NativeTitle.NativeTitleReqExt> {

  private Integer len;

  private Map<String, Object> ext;

  public NativeTitle() {
    setReqExt(new NativeTitleReqExt());
  }

  public static class NativeTitleReqExt extends ReqExt{
  }
}
