package net.media.openrtb24.request;

import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Segment extends AbstractExtensible<Segment.SegReqExt> {

  private String id;

  private String name;

  private String value;

  private Map<String, Object> ext;

  public Segment() {
    setReqExt(new SegReqExt());
  }

  public static class SegReqExt extends ReqExt {
  }
}
