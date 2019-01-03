package net.media.openrtb24.request;

import java.util.Map;

import lombok.Data;

/**
 * Created by rajat.go on 13/10/16.
 */

@Data
public class Format extends AbstractExtensible<Format.FormatReqExt> {

  private Integer w;

  private Integer h;

  private Integer wratio;

  private Integer hratio;

  private Integer wmin;

  private Map<String, Object> ext;

  public Format() {
    setReqExt(new FormatReqExt());
  }

  public class Ext {

  }

  public static class FormatReqExt extends ReqExt {
  }
}
