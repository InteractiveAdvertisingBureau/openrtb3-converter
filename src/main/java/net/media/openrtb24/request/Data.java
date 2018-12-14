package net.media.openrtb24.request;

import java.util.List;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Data extends AbstractExtensible<Data.DataReqExt> {
  private String id;

  private String name;

  private List<Segment> segment;

  private Ext ext;

  public Data() {
    setReqExt(new DataReqExt());
  }


  public class Ext {

  }

  public static class DataReqExt extends ReqExt {
  }
}
