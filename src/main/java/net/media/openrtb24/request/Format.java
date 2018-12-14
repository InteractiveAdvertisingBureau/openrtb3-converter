package net.media.openrtb24.request;

import lombok.Data;

/**
 * Created by rajat.go on 13/10/16.
 */

@Data
public class Format extends AbstractExtensible<Format.FormatReqExt> {

  private Integer w;

  private Integer h;

  private Ext ext;

  public Format() {
    setReqExt(new FormatReqExt());
  }

  public class Ext {

  }

  public static class FormatReqExt extends ReqExt {
  }
}
