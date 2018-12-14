package net.media.openrtb24.request;

/**
 * Created by rajat.go on 14/10/16.
 */

@lombok.Data
public class Regs extends AbstractExtensible<Regs.RegsReqExt> {

  private Integer coppa;

  private Ext ext;

  public Regs()  {
    setReqExt(new RegsReqExt());
  }

  public static class RegsReqExt extends ReqExt {
  }
}
