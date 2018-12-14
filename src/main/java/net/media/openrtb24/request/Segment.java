package net.media.openrtb24.request;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Segment extends AbstractExtensible<Segment.SegReqExt> {

  private String id;

  private String name;

  private String value;

  private Ext ext;

  public Segment() {
    setReqExt(new SegReqExt());
  }

  public static class SegReqExt extends ReqExt {
  }
}
