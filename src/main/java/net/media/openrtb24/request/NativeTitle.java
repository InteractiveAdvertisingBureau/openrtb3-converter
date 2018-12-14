package net.media.openrtb24.request;

import lombok.Data;

@Data
public class NativeTitle extends AbstractExtensible<NativeTitle.NativeTitleReqExt> {

  private Integer len;

  private Ext ext;

  public NativeTitle() {
    setReqExt(new NativeTitleReqExt());
  }

  public static class NativeTitleReqExt extends ReqExt{
  }
}
